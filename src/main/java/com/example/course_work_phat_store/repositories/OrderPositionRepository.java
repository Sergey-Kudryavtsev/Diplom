package com.example.course_work_phat_store.repositories;


import com.example.course_work_phat_store.model.entities.shop.OrderPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderPositionRepository extends JpaRepository<OrderPosition, Integer> {
    @Query(value = "SELECT * FROM order_position_t WHERE color = ?1 AND size =?2 AND product_id = ?3 LIMIT 1;", nativeQuery = true)
    OrderPosition filterOrderPositionByColorAndSizeAndProductId(String color, String size, Integer productId);

    @Query(value = "SELECT * FROM order_position_t WHERE order_id = ?1", nativeQuery = true)
    List<OrderPosition> findAllByOrderId(Integer orderId);
}
