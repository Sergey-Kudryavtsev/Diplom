package com.example.course_work_phat_store.dao.services;

import com.example.course_work_phat_store.model.entities.shop.OrderPosition;

public interface OrderPositionService extends DAO<OrderPosition> {
    OrderPosition filterOrderPositionByColorAndSizeAndProductId(String color, String size, Integer productId);
}
