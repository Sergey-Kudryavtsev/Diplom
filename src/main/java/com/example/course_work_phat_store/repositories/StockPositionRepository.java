package com.example.course_work_phat_store.repositories;



import com.example.course_work_phat_store.model.entities.stock.entities.StockPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPositionRepository extends JpaRepository<StockPosition, Integer> {
    @Query(value = "SELECT * FROM stock_position_t WHERE color = ?1 AND memory_size = ?2 AND product_id = ?3 LIMIT 1;", nativeQuery = true)
    StockPosition findStockPositionByColorAndSizeAndProductId(String color, String memorySize, Integer productId);
}
