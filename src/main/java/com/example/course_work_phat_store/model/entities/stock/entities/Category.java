package com.example.course_work_phat_store.model.entities.stock.entities;

import com.example.course_work_phat_store.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "category_t")
@AllArgsConstructor
public class Category extends BaseEntity {
    @Column(name = "name")
    private String name;

    @OneToMany
    @JoinColumn(name = "category_id")
    private Set<Product> products;

    public Category() {
        products = new HashSet<>();
    }
}
