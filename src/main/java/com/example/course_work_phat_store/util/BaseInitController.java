package com.example.course_work_phat_store.util;

import com.example.course_work_phat_store.dao.services.BrandService;
import com.example.course_work_phat_store.dao.services.CategoryService;
import com.example.course_work_phat_store.dao.services.ProductService;
import com.example.course_work_phat_store.model.entities.itemAttributes.Color;
import com.example.course_work_phat_store.model.entities.itemAttributes.MemorySize;
import com.example.course_work_phat_store.model.entities.stock.entities.Brand;
import com.example.course_work_phat_store.model.entities.stock.entities.Category;
import com.example.course_work_phat_store.model.entities.stock.entities.Product;
import com.example.course_work_phat_store.model.entities.stock.entities.StockPosition;
import com.example.course_work_phat_store.model.secuirty.ApplicationUser;
import com.example.course_work_phat_store.model.secuirty.Role;
import com.example.course_work_phat_store.repositories.ApplicationUserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@RestController
@RequestMapping("/s")
@RequiredArgsConstructor
public class BaseInitController {
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ProductService productService;
    private final ApplicationUserRepository appUserRepo;
    private final PasswordEncoder encoder;
    private Category tempCategory;
    private Brand tempBrand;
    private Product tempProduct;


    @GetMapping("/init")
    public void init() throws IOException {
        if (categoryService.findById(1).isEmpty()) {
            List.of("ТЕЛЕФОН", "ПЛАНШЕТ").forEach(category ->
                    categoryService.save(Category.builder()
                            .name(category)
                            .products(new HashSet<>())
                            .build()));
            List.of("iPhone", "iPad", "SAMSUNG").forEach(brand ->
                    brandService.save(Brand.builder()
                            .name(brand)
                            .products(new HashSet<>())
                            .build()));
            productInit();
            usersInit();
        }
    }

    private void productInit() throws IOException {
        String itemsFile = "items.txt";
        try (Stream<String> stream = Files.lines(Path.of(itemsFile))) {
            List<String> lines = stream.toList();
            for (int i = 0; i < lines.size(); i++) {
                if (i < 5) {
                    if (i == 0) {
                        tempBrand = brandService.findById(1).get();
                        tempCategory = categoryService.findById(1).get();
                    }
                    saveModel(lines.get(i));
                }
                if (4 < i && i < 10) {
                    if (i == 5) {
                        categoryService.save(tempCategory);
                        brandService.save(tempBrand);
                        tempBrand = brandService.findById(3).get();
                        tempCategory = categoryService.findById(1).get();
                    }
                    saveModel(lines.get(i));
                }
                if (9 < i && i < 15) {
                    if (i == 10) {
                        categoryService.save(tempCategory);
                        brandService.save(tempBrand);
                        tempBrand = brandService.findById(2).get();
                        tempCategory = categoryService.findById(2).get();
                    }
                    saveModel(lines.get(i));
                }
                if (14 < i) {
                    if (i == 15) {
                        categoryService.save(tempCategory);
                        brandService.save(tempBrand);
                        tempBrand = brandService.findById(3).get();
                        tempCategory = categoryService.findById(2).get();
                    }
                    saveModel(lines.get(i));
                }
            }
            categoryService.save(tempCategory);
            brandService.save(tempBrand);
        } catch (Exception e) {
            throw new RemoteException();
        }
    }

    private void saveModel(String modelDescription) {
        String[] itemProps = modelDescription
                .substring(1, modelDescription.length() - 2)
                .split(", ");
        saveModelFromStringArray(itemProps);
    }

    private void saveModelFromStringArray(String[] itemProps) {
        tempProduct = Product.builder()
                .article(String.valueOf(new Random().nextInt(1000_000, 10_000_000)))
                .model(itemProps[2])
                .price(Double.parseDouble(itemProps[3]))
                .brand(tempBrand)
                .category(tempCategory)
                .positions(new HashSet<>())
                .build();

        tempCategory.getProducts().add(tempProduct);
        tempBrand.getProducts().add(tempProduct);
        saveModelStockPositions();
        productService.save(tempProduct);
    }

    private void saveModelStockPositions() {
        Arrays.stream(Color.values())
                .forEach(color -> saveStockPositionsByColor(color));
    }

    private void saveStockPositionsByColor(Color color) {
        Arrays.stream(MemorySize.values())
                .forEach(size -> saveStockPositionByColorAndSize(color, size));
    }

    private void saveStockPositionByColorAndSize(Color color, MemorySize size) {
        StockPosition stockPosition = StockPosition.builder()
                .product(tempProduct)
                .color(color)
                .memorySize(size)
                .amount(new Random().nextInt(0, 20))
                .build();
        tempProduct.getPositions().add(stockPosition);
    }

    private void usersInit() {
        ApplicationUser admin = new ApplicationUser("admin@ya.ru", encoder.encode("admin"),"+71234567890","ул.Пушкина д.1", Role.ROLE_ADMIN);
        ApplicationUser user = new ApplicationUser("user@ya.ru", encoder.encode("user"),"+81234567890","ул.Гоголя д.2", Role.ROLE_USER);

        appUserRepo.save(admin);
        appUserRepo.save(user);
    }
}



