package com.example.course_work_phat_store.repositories;


import com.example.course_work_phat_store.model.entities.shop.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = "SELECT * FROM order_t WHERE profile_id = ?1 AND status = 'CART' ORDER BY id ASC LIMIT 1;", nativeQuery = true)
    Order findCartByUserId(Integer userId);
    @Query(value = "SELECT * FROM order_t WHERE status = 'IS_PAID';", nativeQuery = true)
    List<Order> ordersToDeliver();

}
