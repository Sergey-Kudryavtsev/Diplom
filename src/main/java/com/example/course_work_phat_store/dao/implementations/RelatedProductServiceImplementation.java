package com.example.course_work_phat_store.dao.implementations;

import com.example.course_work_phat_store.dao.services.RelatedProductService;
import com.example.course_work_phat_store.model.entities.stock.entities.RelatedProducts;
import com.example.course_work_phat_store.repositories.RelatedProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RelatedProductServiceImplementation implements RelatedProductService {
    private final RelatedProductRepository repo;


    @Override
    public List<RelatedProducts> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<RelatedProducts> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public RelatedProducts save(RelatedProducts relatedProducts) {
        return repo.save(relatedProducts);
    }

    @Override
    public void deleteById(int id) {

    }
}
