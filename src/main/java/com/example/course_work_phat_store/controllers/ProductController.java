package com.example.course_work_phat_store.controllers;

import com.example.course_work_phat_store.dao.services.ApplicationUserService;
import com.example.course_work_phat_store.dao.services.OrderService;
import com.example.course_work_phat_store.dao.services.ProductService;
import com.example.course_work_phat_store.model.entities.itemAttributes.Color;
import com.example.course_work_phat_store.model.entities.itemAttributes.MemorySize;
import com.example.course_work_phat_store.model.entities.itemAttributes.Status;
import com.example.course_work_phat_store.model.entities.shop.Order;
import com.example.course_work_phat_store.model.entities.stock.entities.Product;
import com.example.course_work_phat_store.model.secuirty.ApplicationUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor

public class ProductController {
    private final ProductService productService;
    private final ApplicationUserService applicationUserService;


    @GetMapping
    public String productPage(Model model, @RequestParam Integer productId) {
        if (productId == null) {
            model.addAttribute("error", "Отсутствует обязательный параметр запроса productId");
            return "ui/pages/exception/error";
        }

        Optional<Product> product = productService.findById(productId);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            model.addAttribute("memorySizes", Arrays.stream(MemorySize.values()).map(Enum::name));
            model.addAttribute("colors", Arrays.stream(Color.values()).map(Enum::name));
        } else {
            model.addAttribute("error", "Продукт не найден");
            return "ui/pages/exception/error";
        }
        updateCartCount(model);
        return "ui/pages/product";
    }

    public void updateCartCount(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            ApplicationUser currentUser = applicationUserService.loadUserByUsername(authentication.getName());
            Order cart = currentUserCart(currentUser);
            int cartCount = (cart != null) ? cart.getPositions().size() : 0;
            model.addAttribute("cartCount", cartCount);
        } else {
            model.addAttribute("cartCount", 0);
        }
    }

    public Order currentUserCart(ApplicationUser currentUser) {
        return currentUser.getProfile().getOrders().stream()
                .filter(o -> o.getStatus().equals(Status.CART))
                .findAny()
                .orElse(null); // Возвращаем null, если корзина не найдена
    }

    @GetMapping("/back")
    public String pageBack() {
        return "pageBack";
    }
}


