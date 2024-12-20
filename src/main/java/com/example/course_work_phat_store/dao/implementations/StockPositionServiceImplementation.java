package com.example.course_work_phat_store.dao.implementations;

import com.example.course_work_phat_store.dao.services.StockPositionService;
import com.example.course_work_phat_store.model.entities.stock.entities.StockPosition;
import com.example.course_work_phat_store.repositories.StockPositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockPositionServiceImplementation implements StockPositionService {
    private final StockPositionRepository repo;


    @Override
    public List<StockPosition> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<StockPosition> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public StockPosition save(StockPosition stockPosition) {
        return repo.save(stockPosition);
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public StockPosition findStockPositionByColorAndSizeAndProductId(String color, String memorySize, Integer productId) {
        return repo.findStockPositionByColorAndSizeAndProductId(color, memorySize, productId);
    }

    @Override
    public Optional<StockPosition> getOnePosition(String color, String memorySize, Integer productId) {
        // по переданным данным мы ищем товар на складе
        StockPosition stockPositionToBuy = repo.findStockPositionByColorAndSizeAndProductId(color, memorySize, productId);
        if (stockPositionToBuy.getAmount() > 0) {
            // сохранение "полки" с данным товаром
            return Optional.of(repo.save(stockPositionToBuy));
        }
        return Optional.empty();
    }

    @Override
    public void remove(StockPosition position, int amount) {
        position.setAmount(position.getAmount() - amount);
        repo.save(position);
    }
}
