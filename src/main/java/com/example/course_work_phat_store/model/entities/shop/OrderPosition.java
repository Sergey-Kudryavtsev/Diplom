package com.example.course_work_phat_store.model.entities.shop;

import com.example.course_work_phat_store.model.BaseEntity;
import com.example.course_work_phat_store.model.entities.stock.entities.RelatedProducts;
import com.example.course_work_phat_store.model.entities.stock.entities.StockPosition;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "order_position_t")
@AllArgsConstructor

public class OrderPosition extends BaseEntity {
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "price")
    private double price;
    @ManyToOne
    private Order order;

    @ManyToOne
    @JoinColumn(name = "stock_pos_id")
    private StockPosition stockPosition;

    @OneToMany
    private Set<RelatedProducts> relatedProducts;

    public OrderPosition () {
        relatedProducts = new HashSet<>();
    }

    public double getUnitPrice() {
        // Цена за единицу товара, связанного со складом
        return stockPosition != null && stockPosition.getProduct() != null
                ? stockPosition.getProduct().getPrice()
                : 0.0;
    }

    public double getTotalPrice() {
        // Общая сумма = количество * цена за единицу
        return amount != null ? amount * getUnitPrice() : 0.0;
    }

//    public double getUnitPrice() {
//        return (amount != null && amount > 0) ? price / amount : 0.0;
//    }
//
//    public double getTotalPrice() {
//        return amount != null ? amount * getUnitPrice() : 0.0;
//    }




}

