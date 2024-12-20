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
@Table(name = "brand_t")
@AllArgsConstructor

public class Brand extends BaseEntity {
    @Column(name = "name")
    private String name;

    @OneToMany
    @JoinColumn(name = "brand_id")
    private Set<Product> products;

    public Brand() {
        products = new HashSet<>();
    }
}

