package com.example.course_work_phat_store.controllers;

import com.example.course_work_phat_store.model.secuirty.ApplicationUser;
import com.example.course_work_phat_store.repositories.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.regex.Pattern;

//@Controller
//@RequestMapping("/")
//@RequiredArgsConstructor
//public class UserController {
//    private final PasswordEncoder passwordEncoder;
//    private final ApplicationUserRepository applicationUserRepository;
//
//    @PostMapping("/registration")
//    public String registration(@RequestParam String email, @RequestParam String password, RedirectAttributes redirectAttributes) {
//        Optional<ApplicationUser> loadedUser = applicationUserRepository.findApplicationUserByProfileEmail(email);
//        if (!loadedUser.isPresent()) {
//            ApplicationUser applicationUser = new ApplicationUser(email, passwordEncoder.encode(password));
//            applicationUserRepository.save(applicationUser);
//            return "redirect:/";
//        } else {
//            // Добавляем сообщение в FlashAttributes
//            redirectAttributes.addFlashAttribute("errorMessage", "Пользователь с таким email уже зарегистрирован.");
//            return "redirect:/registration";
//        }
//    }
//}

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserRepository applicationUserRepository;

    private static final String PHONE_REGEX = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";

    @PostMapping("/registration")
    public String registration(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String phone,
            @RequestParam String address,
            RedirectAttributes redirectAttributes) {

        // Проверка регулярного выражения для телефона
        if (!Pattern.matches(PHONE_REGEX, phone)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Неверный формат номера телефона.");
            return "redirect:/registration";
        }

        Optional<ApplicationUser> loadedUser = applicationUserRepository.findApplicationUserByProfileEmail(email);
        if (!loadedUser.isPresent()) {
            ApplicationUser applicationUser = new ApplicationUser(email, passwordEncoder.encode(password), phone, address);
            applicationUserRepository.save(applicationUser);
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Пользователь с таким email уже зарегистрирован.");
            return "redirect:/registration";
        }
    }
}

//@Controller
//@RequestMapping("/")
//@RequiredArgsConstructor
//public class UserController {
//    private final PasswordEncoder passwordEncoder;
//    private final ApplicationUserRepository applicationUserRepository;
//
//    @PostMapping("/registration")
//    public String registration(@RequestParam String email,
//                               @RequestParam String password,
//                               @RequestParam String phone,
//                               @RequestParam String address,
//                               RedirectAttributes redirectAttributes) {
//        Optional<ApplicationUser> loadedUser = applicationUserRepository.findApplicationUserByProfileEmail(email);
//        if (!loadedUser.isPresent()) {
//            // Создаём нового пользователя с новыми полями
//            ApplicationUser applicationUser = new ApplicationUser(email, passwordEncoder.encode(password), phone, address);
//            applicationUserRepository.save(applicationUser);
//            return "redirect:/";
//        } else {
//            // Добавляем сообщение в FlashAttributes
//            redirectAttributes.addFlashAttribute("errorMessage", "Пользователь с таким email уже зарегистрирован.");
//            return "redirect:/registration";
//        }
//    }
//}