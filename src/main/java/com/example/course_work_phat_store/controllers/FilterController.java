package com.example.course_work_phat_store.controllers;

import com.example.course_work_phat_store.dao.services.*;
import com.example.course_work_phat_store.model.entities.itemAttributes.Status;
import com.example.course_work_phat_store.model.entities.shop.Order;
import com.example.course_work_phat_store.model.entities.stock.entities.Brand;
import com.example.course_work_phat_store.model.entities.stock.entities.Category;
import com.example.course_work_phat_store.model.entities.stock.entities.Product;
import com.example.course_work_phat_store.model.secuirty.ApplicationUser;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/filter")
@RequiredArgsConstructor
public class FilterController {
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ProductService productService;
    private final ApplicationUserService applicationUserService;

    @GetMapping
    public String filter(Model model,
                         @RequestParam(required = false) Integer categoryId,
                         @RequestParam(required = false) Integer brandId,
                         @RequestParam(required = false) Double minPrice,
                         @RequestParam(required = false) Double maxPrice){
        // Обновляем счетчик
        updateCartCount(model);

        if (minPrice == null && brandId != null) {
            filterByBrandId(model, brandId);
        } else if (categoryId != null && minPrice == null) {
            filterByCategoryId(model, categoryId);
        } else {
            switchFilter(model, categoryId, brandId, minPrice, maxPrice);
        }

        return "ui/pages/filterResult";
    }

    private void updateCartCount(Model model) {
        // Проверяем, аутентифицирован ли пользователь
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            ApplicationUser currentUser = applicationUserService.loadUserByUsername(authentication.getName());
            Order cart = currentUserCart(currentUser);
            int cartCount = (cart != null) ? cart.getPositions().size() : 0;
            model.addAttribute("cartCount", cartCount);
        } else {
            model.addAttribute("cartCount", 0); // Устанавливаем 0 для незарегистрированных пользователей
        }
    }

    private Order currentUserCart(ApplicationUser currentUser) {
        return currentUser.getProfile().getOrders().stream()
                .filter(o -> o.getStatus().equals(Status.CART))
                .findAny()
                .orElse(null);
    }

    // Метод для фильтрации изначально политаров
    private void filterByCategoryIdAndPriceBetween(Model model, Integer categoryId, Double minPrice, Double maxPrice) {
        List<Product> products = productService.filterByCategoryIdAndPriceBetween(categoryId, minPrice, maxPrice)
                .stream()
                .filter(Product::isInStock)
                .toList();
        model.addAttribute("products", products);
    }

    private void filterByBrandIdAndPriceBetween(Model model, Integer brandId, Double minPrice, Double maxPrice) {
        List<Product> products = productService.filterByBrandIdAndPriceBetween(brandId, minPrice, maxPrice)
                .stream()
                .filter(Product::isInStock)
                .toList();
        model.addAttribute("products", products);
    }

    private void filterByCategoryIdAndBrandIdAndPriceBetween(Model model, Integer brandId, Integer categoryId, Double minPrice, Double maxPrice) {
        List<Product> products = productService.filterByCategoryIdAndBrandIdAndPriceBetween(categoryId, brandId, minPrice, maxPrice)
                .stream()
                .filter(Product::isInStock)
                .toList();
        model.addAttribute("products", products);
    }

    public void switchFilter(Model model, Integer categoryId, Integer brandId, Double minPrice, Double maxPrice) {
        if (categoryId == null && brandId != null) {
            filterByBrandIdAndPriceBetween(model, brandId, minPrice, maxPrice);
        }
        if (categoryId != null && brandId == null) {
            filterByCategoryIdAndPriceBetween(model, categoryId, minPrice, maxPrice);
        }
        if (categoryId != null && brandId != null) {
            filterByCategoryIdAndBrandIdAndPriceBetween(model, categoryId, brandId, minPrice, maxPrice);
        }
    }

    @GetMapping("/page")
    public String pages(Model model) {
        updateCartCount(model);
        List<Brand> allBrand = brandService.findAll();
        model.addAttribute("brands", allBrand);

        List<Category> allCategories = categoryService.findAll();
        model.addAttribute("categories", allCategories);

        Double minPrice = productService.minPrice();
        model.addAttribute("minPrice", minPrice);

        Double maxPrice = productService.maxPrice();
        model.addAttribute("maxPrice", maxPrice);

        return "ui/pages/search";
    }

    private void filterByCategoryId(Model model, Integer categoryId) {
        List<Product> products = productService.filterByCategoryId(categoryId)
                .stream()
                .filter(Product::isInStock)
                .toList();
        model.addAttribute("products", products);
    }

    private void filterByBrandId(Model model, Integer brandId) {
        List<Product> products = productService.filterByBrandId(brandId)
                .stream()
                .filter(Product::isInStock)
                .toList();
        model.addAttribute("products", products);
    }
}