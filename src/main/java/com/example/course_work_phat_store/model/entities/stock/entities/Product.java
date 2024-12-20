package com.example.course_work_phat_store.model.entities.stock.entities;

import com.example.course_work_phat_store.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "product_t")
@AllArgsConstructor
public class Product extends BaseEntity {
    @Column(name = "article", unique = true)
    private String article;
    @Column(name = "model")
    private String model;
    @Column(name = "price")
    private Double price;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private Category category;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Set<StockPosition> positions;

    public Product() {
        positions = new HashSet<>();
    }

    public boolean isInStock(){ //для фильтра контроллера
        return !positions.stream()
                .filter(position -> position.getAmount()>0)
                .findFirst()
                .isEmpty();
    }
}
