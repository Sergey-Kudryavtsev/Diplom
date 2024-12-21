package com.example.course_work_phat_store.controllers;

import com.example.course_work_phat_store.dao.services.ApplicationUserService;
import com.example.course_work_phat_store.dao.services.OrderService;
import com.example.course_work_phat_store.model.entities.itemAttributes.Status;
import com.example.course_work_phat_store.model.entities.shop.Order;
import com.example.course_work_phat_store.model.secuirty.ApplicationUser;
import com.example.course_work_phat_store.repositories.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

//@Controller
//@RequestMapping("/profile")
//@RequiredArgsConstructor
//public class ProfileController {
//    private final ApplicationUserRepository applicationUserRepository;
//
//    // Метод для отображения страницы профиля
//    @GetMapping
//    public String profilePage(Model model, Authentication authentication) {
//        String email = authentication.getName();
//        Optional<ApplicationUser> user = applicationUserRepository.findApplicationUserByProfileEmail(email);
//
//        if (user.isPresent()) {
//            model.addAttribute("applicationUser", user.get());
//            return "ui/pages/profile";
//        }
//
//        return "redirect:/login"; // если пользователь не найден, перенаправляем на страницу логина
//    }
//
//    // Метод для обновления данных пользователя
//    @PostMapping("/update")
//    public String updateProfile(@ModelAttribute ApplicationUser updatedUser,
//                                Authentication authentication,
//                                RedirectAttributes redirectAttributes) {
//        String email = authentication.getName();
//        Optional<ApplicationUser> userOptional = applicationUserRepository.findApplicationUserByProfileEmail(email);
//
//        if (userOptional.isPresent()) {
//            ApplicationUser user = userOptional.get();
//            user.setUsername(updatedUser.getUsername());
//            user.setPhone(updatedUser.getPhone());
//            user.setAddress(updatedUser.getAddress());
//            applicationUserRepository.save(user);
//
//            redirectAttributes.addFlashAttribute("successMessage", "Данные профиля успешно обновлены.");
//            return "redirect:/profile";
//        }
//
//        redirectAttributes.addFlashAttribute("errorMessage", "Не удалось обновить данные профиля.");
//        return "redirect:/profile";
//    }
//}

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ApplicationUserRepository applicationUserRepository;
    private final ApplicationUserService applicationUserService;



    @GetMapping
    public String profilePage(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
            return "redirect:/login";
        }

        String email = authentication.getName();
        Optional<ApplicationUser> user = applicationUserRepository.findApplicationUserByProfileEmail(email);

        if (user.isPresent()) {
            model.addAttribute("applicationUser", user.get());
            updateCartCount(model); // Обновляем счетчик корзины
            return "ui/pages/profile";
        }

        return "redirect:/login";
    }

    // Метод для обновления данных пользователя
    @PostMapping("/update")
    public String updateProfile(@ModelAttribute ApplicationUser updatedUser,
                                Authentication authentication,
                                RedirectAttributes redirectAttributes) {
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
            return "redirect:/login";
        }

        String email = authentication.getName();
        Optional<ApplicationUser> userOptional = applicationUserRepository.findApplicationUserByProfileEmail(email);

        if (userOptional.isPresent()) {
            ApplicationUser user = userOptional.get();
            user.setUsername(updatedUser.getUsername());
            user.setPhone(updatedUser.getPhone());
            user.setAddress(updatedUser.getAddress());
            applicationUserRepository.save(user);

            redirectAttributes.addFlashAttribute("successMessage", "Данные профиля успешно обновлены.");
            return "redirect:/profile";
        }

        redirectAttributes.addFlashAttribute("errorMessage", "Не удалось обновить данные профиля.");
        return "redirect:/profile";
    }

    private void updateCartCount(Model model) {
        ApplicationUser currentUser = currentApplicationUser();
        int cartCount = 0;
        if (currentUser != null) {
            Order cart = currentUserCart(currentUser);
            cartCount = (cart != null) ? cart.getPositions().size() : 0;
        }
        model.addAttribute("cartCount", cartCount);
    }

    private ApplicationUser currentApplicationUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return applicationUserService.loadUserByUsername(email);
    }

    private Order currentUserCart(ApplicationUser currentUser) {
        return currentUser.getProfile().getOrders().stream()
                .filter(o -> o.getStatus().equals(Status.CART))
                .findAny().orElse(null);
    }
}

