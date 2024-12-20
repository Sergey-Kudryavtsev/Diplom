package com.example.course_work_phat_store.repositories;

import com.example.course_work_phat_store.model.entities.stock.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT * FROM product_t WHERE category_id = ?1 AND price BETWEEN ?3 AND ?2;", nativeQuery = true)
    List<Product> filterByCategoryIdAndPriceBetween(int categoryId, double minPrice, double maxPrice);


    @Query(value = "SELECT * FROM product_t WHERE brand_id =:brandId AND price<:minPrice AND price>:maxPrice", nativeQuery = true)
    List<Product> filterByBrandIdAndPriceBetween(@Param("brandId") int brandId, @Param("minPrice") double minPrice, @Param("maxPrice")  double maxPrice);

    @Query(value = "SELECT * FROM product_t WHERE brand_id =:brandId AND category_id =:categoryId AND price<:minPrice  AND price>:maxPrice", nativeQuery = true)
    List<Product> filterByCategoryIdAndBrandIdAndPriceBetween(@Param("categoryId") int categoryId, @Param("brandId") int brandId, @Param("minPrice") double minPrice, @Param("maxPrice")  double maxPrice);

    @Query(value = "SELECT * FROM product_t WHERE category_id = :categoryId",nativeQuery = true)
    List<Product> queryCategoryId(@Param("categoryId") int categoryId);
    @Query(value = "SELECT * FROM product_t WHERE brand_id = :brandId",nativeQuery = true)
    List<Product> queryBrandId(@Param("brandId") int brandId);



    @Query(value = "SELECT MAX(price) FROM product_t", nativeQuery = true)
    Double maxPrice();
    @Query(value = "SELECT MIN(price) FROM product_t", nativeQuery = true)
    Double minPrice();
}
