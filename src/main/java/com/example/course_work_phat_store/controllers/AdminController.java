package com.example.course_work_phat_store.controllers;

import com.example.course_work_phat_store.dao.services.CategoryService;
import com.example.course_work_phat_store.dao.services.OrderService;
import com.example.course_work_phat_store.dao.services.ProductService;
import com.example.course_work_phat_store.model.entities.shop.Order;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final OrderService orderService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping
    public String adminsPage(Model model) {
        List<Order> ordersToDeliver = orderService.ordersToDeliver();
        model.addAttribute("orders", ordersToDeliver);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", productService.findAll());
        return "ui/pages/admin/adminHomePage";
    }

    // Добавление товара
    @PostMapping("/addProduct")
    public String addProduct(@RequestParam String name, @RequestParam double price, @RequestParam int categoryId) {
        productService.addProduct(name, price, categoryId);
        return "redirect:/admin";
    }

    // Удаление товара
    @PostMapping("/deleteProduct")
    public String deleteProduct(@RequestParam int productId) {
        productService.deleteProduct(productId);
        return "redirect:/admin";
    }

    @PostMapping("/deliver")
    public String deliver(@RequestParam int orderId) {
        orderService.deliver(orderId);
        return "redirect:/admin";
    }

    @PostMapping("/decline")
    public String decline(@RequestParam int orderId) {

        return "redirect:/admin";
    }


    // Добавление категории
    @PostMapping("/add")
    public String addCategory(@RequestParam String name) {
        categoryService.addCategory(name);
        return "redirect:/admin";
    }

    // Удаление категории
    @PostMapping("/deleteCategory")
    public String deleteCategory(@RequestParam int categoryId) {
        categoryService.deleteCategory(categoryId);
        return "redirect:/admin";
    }
}
