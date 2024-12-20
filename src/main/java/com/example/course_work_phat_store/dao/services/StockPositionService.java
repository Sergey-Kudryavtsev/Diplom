package com.example.course_work_phat_store.dao.services;

import com.example.course_work_phat_store.model.entities.stock.entities.StockPosition;
import jakarta.transaction.Transactional;

import java.util.Optional;

public interface StockPositionService extends DAO<StockPosition> {
    StockPosition findStockPositionByColorAndSizeAndProductId(String color, String memorySize, Integer productId);

    @Transactional
    Optional<StockPosition> getOnePosition(String color, String memorySize, Integer productId);

    void remove(StockPosition position, int amount);

}
