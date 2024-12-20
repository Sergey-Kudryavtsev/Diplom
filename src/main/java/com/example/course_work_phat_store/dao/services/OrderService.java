package com.example.course_work_phat_store.dao.services;

import com.example.course_work_phat_store.model.entities.shop.Order;
import jakarta.transaction.Transactional;

import java.util.List;

public interface OrderService extends DAO<Order> {

    Order findCartByUserId(Integer userId);
    @Transactional
    void addToCart(String email, String color, String memorySize, Integer productId);
    Order findByOrderPositionId(int orderPositionId);

    @Transactional
    void pay(Order cart);
    List<Order> ordersToDeliver();
    void deliver(int orderId);
}
