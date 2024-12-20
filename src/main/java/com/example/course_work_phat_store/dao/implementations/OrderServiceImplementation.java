package com.example.course_work_phat_store.dao.implementations;

import com.example.course_work_phat_store.dao.services.*;
import com.example.course_work_phat_store.model.entities.itemAttributes.Status;
import com.example.course_work_phat_store.model.entities.shop.Order;
import com.example.course_work_phat_store.model.entities.shop.OrderPosition;
import com.example.course_work_phat_store.model.entities.shop.Profile;
import com.example.course_work_phat_store.model.entities.stock.entities.Product;
import com.example.course_work_phat_store.model.entities.stock.entities.StockPosition;
import com.example.course_work_phat_store.model.secuirty.ApplicationUser;
import com.example.course_work_phat_store.repositories.OrderPositionRepository;
import com.example.course_work_phat_store.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImplementation implements OrderService {


    @Value("${spring.mail.username}")
    private String emailFrom;
    private final OrderRepository repo;
    private final StockPositionService stockPositionService;
    private final ApplicationUserService applicationUserService;
    private final OrderPositionService orderPositionService;
    private final ProfileService profileService;
    private final ProductService productService;

    private final JavaMailSender mailSender;


    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Optional<Order> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Order save(Order order) {
        return repo.save(order);
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public Order findCartByUserId(Integer profileId) {
        return repo.findCartByUserId(profileId);

    }

    @Override
    public void addToCart(String email, String color, String memorySize, Integer productId) {
        ApplicationUser loggedUser = applicationUserService.loadUserByUsername(email);
        // по id юзера ищем корзину (единственный неоплаченный заказ)
        Order cart = findCartByUserId(loggedUser.getProfile().getId());
        // получаем 1 единицу товара со склада
        Optional<StockPosition> stockPositionToBuy = stockPositionService.getOnePosition(color, memorySize, productId);
        // на основе позиции со склада, которую собрался приобретать покупатель, формируем запись в чеке (корзине)
//        if (stockPositionToBuy.isPresent()) {
        if (!stockPositionToBuy.isPresent()) {
            throw new IllegalArgumentException("Такого товара нет в наличии: цвет=" + color + ", память=" + memorySize + ", ID товара=" + productId);
        }
        OrderPosition positionToAdd = OrderPosition.builder()
                .amount(1)
                .stockPosition(stockPositionToBuy.get())
                .order(cart)
                .build();
        addToCart(cart, positionToAdd);
        repo.save(cart);
    }


    @Override
    public Order findByOrderPositionId(int orderPositionId) {
        return orderPositionService.findById(orderPositionId).get().getOrder();
    }

    @Override
    public void pay(Order cart) {
        cart.setStatus(Status.IS_PAID);
        repo.save(cart);
        removeFromStock(cart);
        Profile currentProfile = cart.getProfile();
        currentProfile.getOrders().add(
                Order.builder()
                        .status(Status.CART)
                        .profile(currentProfile)
                        .positions(new HashSet<>())
                        .build()
        );
        profileService.save(currentProfile);
    }

    @Override
    public List<Order> ordersToDeliver() {
        return repo.ordersToDeliver();
    }

    private void addToCart(Order cart, OrderPosition positionToAdd) {
        // позиция на складе (товар) для дополнения корзины
        StockPosition stockPosition = positionToAdd.getStockPosition();
        // Если товар присутствует в корзине, изменяем его количество, в противном случае, добавляем новую строку в корзину
        if (isStockPositionPresent(cart, stockPosition)) {
            OrderPosition positionToIncrementAmount = cart.getPositions().stream()
                    .filter(orderPosition -> orderPosition.getStockPosition().getId() == stockPosition.getId())
                    .findFirst().get();
            if (stockPosition.getAmount() > positionToIncrementAmount.getAmount()) {
                positionToIncrementAmount.setAmount(positionToIncrementAmount.getAmount() + 1);
            }
        } else {
            if (stockPosition.getAmount() > 0) {
                // добавляем запись в чек (корзину)
                cart.getPositions().add(positionToAdd);
            } else {
                throw new IllegalArgumentException("Недостаточно товара на складе для добавления в корзину.");
            }
        }
    }

    @Override
    public void deliver(int orderId) {
        Order orderToDeliver = repo.findById(orderId).get();
        orderToDeliver.setStatus(Status.IS_DELIVERED);
        repo.save(orderToDeliver);

        String subject = "Заказ оплачен";
        String messageBody = "Заказ будет доставлен по адресу: " + orderToDeliver.getProfile().getAddress();
        String emailTo = orderToDeliver.getProfile().getEmail();
        sendDeliveryEmail(subject, emailTo, messageBody);
    }

    private void sendDeliveryEmail(String subject, String emailTo, String messageBody) {
        SimpleMailMessage message = createMailMessage(subject, emailTo, messageBody);
        mailSender.send(message);
    }

    private SimpleMailMessage createMailMessage(String subject, String emailTo, String messageBody) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(messageBody);
        mailMessage.setFrom(emailFrom);
        return mailMessage;
    }

    private boolean isStockPositionPresent(Order cart, StockPosition stockPosition) {
        return cart.getPositions().stream()
                .anyMatch(orderPosition -> orderPosition.getStockPosition().getId() == stockPosition.getId());
    }

    private void removeFromStock(Order cart) {
        cart.getPositions().forEach(p -> stockPositionService.remove(p.getStockPosition(), p.getAmount()));
    }
}

