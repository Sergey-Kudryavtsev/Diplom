package com.example.course_work_phat_store.controllers;

import com.example.course_work_phat_store.dao.services.ApplicationUserService;
import com.example.course_work_phat_store.dao.services.CategoryService;
import com.example.course_work_phat_store.dao.services.OrderService;
import com.example.course_work_phat_store.model.entities.itemAttributes.Status;
import com.example.course_work_phat_store.model.entities.shop.Order;
import com.example.course_work_phat_store.model.entities.stock.dto.CategoryDTO;
import com.example.course_work_phat_store.model.secuirty.ApplicationUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ViewController {
    private final CategoryService categoryService;
    private final ApplicationUserService applicationUserService;

    @GetMapping
    public String index(Model model, Authentication authentication) {
        updateCartCount(model, authentication);

        List<CategoryDTO> categories = categoryService.findAll().stream()
                .map(category -> CategoryDTO.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build())
                .toList();

        model.addAttribute("categories", categories);
        return "ui/pages/index";
    }

    private void updateCartCount(Model model, Authentication authentication) {
        int cartCount = 0;
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            ApplicationUser currentUser = applicationUserService.loadUserByUsername(authentication.getName());
            Order cart = currentUserCart(currentUser);
            cartCount = (cart != null) ? cart.getPositions().size() : 0;
        }
        model.addAttribute("cartCount", cartCount);
    }

    private Order currentUserCart(ApplicationUser currentUser) {
        return currentUser.getProfile().getOrders().stream()
                .filter(o -> o.getStatus().equals(Status.CART))
                .findAny().orElse(null);
    }

    @GetMapping("/login")
    public String loginPage() {
        return "ui/pages/login";
    }

    @GetMapping("/registration")
    public String registrationPage() {
        return "ui/pages/registration";
    }
}