package com.example.course_work_phat_store.dao.implementations;

import com.example.course_work_phat_store.dao.services.CategoryService;
import com.example.course_work_phat_store.model.entities.stock.entities.Category;
import com.example.course_work_phat_store.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImplementation implements CategoryService {
    private final CategoryRepository repo;

    @Override
    public List<Category> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Category> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public Category save(Category category) {
        return repo.save(category);
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void addCategory(String name) {
        Category newCategory = new Category();
        newCategory.setName(name);
        repo.save(newCategory);
    }

    @Override
    public void deleteCategory(int categoryId) {
        repo.deleteById(categoryId);
    }
}
