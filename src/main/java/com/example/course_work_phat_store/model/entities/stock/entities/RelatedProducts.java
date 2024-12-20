package com.example.course_work_phat_store.model.entities.stock.entities;

import com.example.course_work_phat_store.model.BaseEntity;
import com.example.course_work_phat_store.model.entities.shop.OrderPosition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "related_products_t")
@AllArgsConstructor
@NoArgsConstructor
public class RelatedProducts extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Double price;
    @Column(name = "note")
    private String note;

}
