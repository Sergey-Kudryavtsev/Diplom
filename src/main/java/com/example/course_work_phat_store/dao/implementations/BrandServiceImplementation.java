package com.example.course_work_phat_store.dao.implementations;

import com.example.course_work_phat_store.dao.services.BrandService;
import com.example.course_work_phat_store.model.entities.stock.entities.Brand;
import com.example.course_work_phat_store.repositories.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandServiceImplementation implements BrandService {
    private final BrandRepository repo;

    @Override
    public List<Brand> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Brand> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public Brand save(Brand brand) {
        return repo.save(brand);
    }

    @Override
    public void deleteById(int id) {
        repo.deleteById(id);
    }
}
