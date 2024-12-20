package com.example.course_work_phat_store.model.entities.shop;

import com.example.course_work_phat_store.model.BaseEntity;
import com.example.course_work_phat_store.model.entities.itemAttributes.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@Entity
@Table(name = "order_t")
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    private Profile profile;
    @Column(name = "total_price")
    private double totalPrice; // Поле для хранения общей стоимости
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private Set<OrderPosition> positions;

    public double getPrice() {
        return positions.stream()
                .reduce(0.0,
                        (acc, cur) -> acc = acc + cur.getAmount() * cur.getStockPosition().getProduct().getPrice(),
                        Double::sum
                );
    }
}
