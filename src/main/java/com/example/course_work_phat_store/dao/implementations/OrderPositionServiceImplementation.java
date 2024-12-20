package com.example.course_work_phat_store.dao.implementations;


import com.example.course_work_phat_store.dao.services.OrderPositionService;
import com.example.course_work_phat_store.model.entities.shop.OrderPosition;
import com.example.course_work_phat_store.repositories.BrandRepository;
import com.example.course_work_phat_store.repositories.OrderPositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderPositionServiceImplementation implements OrderPositionService {
    private final OrderPositionRepository repo;


    @Override
    public List<OrderPosition> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<OrderPosition> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public OrderPosition save(OrderPosition orderPosition) {
        return repo.save(orderPosition);
    }

    @Override
    public void deleteById(int id) {
        repo.deleteById(id);
    }


    @Override
    public OrderPosition filterOrderPositionByColorAndSizeAndProductId(String color, String size, Integer productId) {
        return repo.filterOrderPositionByColorAndSizeAndProductId(color, size, productId);
    }
}
