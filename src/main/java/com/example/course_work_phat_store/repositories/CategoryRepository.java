package com.example.course_work_phat_store.repositories;

import com.example.course_work_phat_store.model.entities.stock.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
