package com.example.course_work_phat_store.controllers;

import lombok.RequiredArgsConstructor;
import com.example.course_work_phat_store.dao.services.ApplicationUserService;
import com.example.course_work_phat_store.dao.services.OrderPositionService;
import com.example.course_work_phat_store.dao.services.OrderService;
import com.example.course_work_phat_store.model.entities.itemAttributes.Status;
import com.example.course_work_phat_store.model.entities.shop.Order;
import com.example.course_work_phat_store.model.entities.shop.OrderPosition;
import com.example.course_work_phat_store.model.secuirty.ApplicationUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final OrderService orderService;
    private final ApplicationUserService applicationUserservice;
    private final OrderPositionService orderPositionService;

    @GetMapping
    public String cartPage(Model model) {
        ApplicationUser currentUser = currentApplicationUser();
        Order cart = currentUserCart(currentUser);
        model.addAttribute("cart", cart);

        double totalPrice = calculateTotalPrice(cart);
        model.addAttribute("totalPrice",totalPrice);
        updateCartCount(model);
        return "ui/pages/cart";
    }

   public void updateCartCount(Model model) {
        ApplicationUser currentUser = currentApplicationUser();

        int cartCount = 0;
        if (currentUser != null) {
            Order cart = currentUserCart(currentUser);
            cartCount = (cart != null) ? cart.getPositions().size() : 0;
        }
        model.addAttribute("cartCount", cartCount); // Добавляем количество товаров в модель
    }


    @PostMapping("/add")
    @ResponseBody
    public void addToCart(@RequestParam String color, @RequestParam String memorySize, @RequestParam Integer productId){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        orderService.addToCart(email, color, memorySize, productId);
    }

    @PostMapping("/delete")
    public String deletePosition(@RequestParam int positionId) {
        orderPositionService.deleteById(positionId);
        return "redirect:/cart";
    }

//Метод для работы с + - в корзине.
    @PostMapping("/addTake")
    public String addToCartTake(@RequestParam String color, @RequestParam String memorySize, @RequestParam Integer productId){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        orderService.addToCart(email, color, memorySize, productId);
        return "redirect:/cart";
    }

    @PostMapping("/drop")
    public String deleteOneFromOrderPosition(@RequestParam int positionId) {
        OrderPosition orderPositionToUpdate = orderPositionService.findById(positionId).get();
        orderPositionToUpdate.setAmount(orderPositionToUpdate.getAmount() - 1);
        if (orderPositionToUpdate.getAmount() == 0) {
            orderPositionService.deleteById(positionId);
        } else {
            orderPositionService.save(orderPositionToUpdate);
        }
        return "redirect:/cart";
    }

    //  для получения количества позиций в корзине
    @GetMapping("/count")
    @ResponseBody
    public int getCartCount() {
        ApplicationUser currentUser = currentApplicationUser();
        Order cart = currentUserCart(currentUser);
        return cart != null ? cart.getPositions().size() : 0;
    }

    public double calculateTotalPrice(Order cart) {
        return cart.getPositions().stream()
                .mapToDouble(OrderPosition::getTotalPrice)
                .sum();
    }

    @PostMapping("/pay")
    public String pay(RedirectAttributes ra) {
        ApplicationUser currentUser = currentApplicationUser();
        Order cart = currentUserCart(currentUser);
        double price = cart.getPrice();
        // Выясняем, есть ли возможность собрать заказ
        if (canBeDelivered(cart)) {
            if (currentUser.getProfile().pay(price)) {
                orderService.pay(cart);
                return "redirect:/";
            } else {
                ra.addFlashAttribute("payError", "заказ не был оплачен");
                return "redirect:/cart";
            }
        } else {
            ra.addFlashAttribute("deliveryError", "заказ невозможно собрать - часть товара нет в наличии");
            return "redirect:/cart";
        }
    }

    @PostMapping("/match")
    public String match() {
        Order cart = currentUserCart(currentApplicationUser());
        cart.getPositions().forEach(p -> matchPosition(p));
        return "redirect:/cart";
    }

    private void matchPosition(OrderPosition position) {
        if (position.getAmount() > position.getStockPosition().getAmount()) {
            position.setAmount(position.getStockPosition().getAmount());
            orderPositionService.save(position);
        }
    }

    private Order currentUserCart(ApplicationUser currentUser) {
        return currentUser.getProfile().getOrders().stream()
                .filter(o -> o.getStatus().equals(Status.CART))
                .sorted(Comparator.comparingInt(o ->
                        o.getPositions().stream().mapToInt(OrderPosition::getId).min().orElse(Integer.MAX_VALUE)))
                .findAny().get();
    }

    private ApplicationUser currentApplicationUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return applicationUserservice.loadUserByUsername(email);
    }

    private boolean canBeDelivered(Order cart) {
        AtomicBoolean canBeDelivered = new AtomicBoolean(true);
        cart.getPositions().forEach(p -> {
            if (!canStockPositionBeDelivered(p)) {
                canBeDelivered.set(false);
            }
        });
        return canBeDelivered.get();
    }

    private boolean canStockPositionBeDelivered(OrderPosition position) {
        return position.getStockPosition().getAmount() >= position.getAmount();
    }
}
