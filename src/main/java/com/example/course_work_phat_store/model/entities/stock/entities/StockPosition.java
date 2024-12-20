package com.example.course_work_phat_store.model.entities.stock.entities;

import com.example.course_work_phat_store.model.BaseEntity;
import com.example.course_work_phat_store.model.entities.itemAttributes.Color;
import com.example.course_work_phat_store.model.entities.itemAttributes.MemorySize;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "stock_position_t")
@AllArgsConstructor
@NoArgsConstructor
public class StockPosition extends BaseEntity {
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "memory_size")
    @Enumerated(EnumType.STRING)
    private MemorySize memorySize;

    @Column(name = "color")
    @Enumerated(EnumType.STRING)
    private Color color;

    @ManyToOne
    private Product product;
}
