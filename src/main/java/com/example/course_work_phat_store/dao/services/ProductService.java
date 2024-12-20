package com.example.course_work_phat_store.dao.services;

import com.example.course_work_phat_store.model.entities.stock.entities.Product;

import java.util.List;

public interface ProductService extends DAO<Product> {
    List<Product> filterByCategoryIdAndPriceBetween(int categoryId, double minPrice, double maxPrice);

    List<Product> filterByBrandIdAndPriceBetween(int brandId, double minPrice, double maxPrice);

    List<Product> filterByCategoryIdAndBrandIdAndPriceBetween(int categoryId, int brandId, double minPrice, double maxPrice);

    Double maxPrice();

    Double minPrice();

    List<Product> filterByCategoryId(int categoryId);

    List<Product> filterByBrandId(int brandId);

    void addProduct(String name, double price, int categoryId);

    void deleteProduct(int productId);
}
