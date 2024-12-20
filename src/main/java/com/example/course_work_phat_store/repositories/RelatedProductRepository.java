package com.example.course_work_phat_store.repositories;

import com.example.course_work_phat_store.model.entities.stock.entities.RelatedProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelatedProductRepository extends JpaRepository<RelatedProducts, Integer> {
}
