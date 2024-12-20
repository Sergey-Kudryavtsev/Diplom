package com.example.course_work_phat_store.dao.implementations;

import com.example.course_work_phat_store.dao.services.ProductService;
import com.example.course_work_phat_store.model.entities.stock.entities.Category;
import com.example.course_work_phat_store.model.entities.stock.entities.Product;
import com.example.course_work_phat_store.repositories.BrandRepository;
import com.example.course_work_phat_store.repositories.CategoryRepository;
import com.example.course_work_phat_store.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImplementation implements ProductService {
    private final ProductRepository repo;
    private final CategoryRepository categoryRepository;


    @Override
    public List<Product> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Product> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public Product save(Product product) {
        return repo.save(product);
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public List<Product> filterByCategoryIdAndPriceBetween(int categoryId, double minPrice, double maxPrice) {
        List<Product> products = repo.filterByCategoryIdAndPriceBetween(categoryId, maxPrice, minPrice);
        return products;

    }

    @Override
    public List<Product> filterByBrandIdAndPriceBetween(int brandId, double minPrice, double maxPrice) {
        return repo.filterByBrandIdAndPriceBetween(brandId, maxPrice, minPrice);
    }

    @Override
    public List<Product> filterByCategoryIdAndBrandIdAndPriceBetween(int categoryId, int brandId, double minPrice, double maxPrice) {
        return repo.filterByCategoryIdAndBrandIdAndPriceBetween(categoryId, brandId, maxPrice, minPrice);
    }

    @Override
    public Double maxPrice() {
        return repo.maxPrice();
    }

    @Override
    public Double minPrice() {
        return repo.minPrice();
    }

    @Override
    public List<Product> filterByCategoryId(int categoryId) {
        return repo.queryCategoryId(categoryId);
    }

    @Override
    public List<Product> filterByBrandId(int brandId) {
        return repo.queryBrandId(brandId);
    }

    @Override
    public void addProduct(String model, double price, int categoryId) {
        // Найти категорию по ID
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Категория не найдена"));

        // Создать новый продукт
        Product product = new Product();
        product.setModel(model);
        product.setPrice(price);
        product.setCategory(category);

        // Сохранить продукт в базе данных
        repo.save(product);
    }

    @Override
    public void deleteProduct(int productId) {
        // Пытаемся найти продукт по ID и удаляем его, если он существует
        if (repo.existsById(productId)) {
            repo.deleteById(productId);
        } else {
            throw new IllegalArgumentException("Продукт не найден");
        }
    }


}
