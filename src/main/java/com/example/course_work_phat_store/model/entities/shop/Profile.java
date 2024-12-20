package com.example.course_work_phat_store.model.entities.shop;

import com.example.course_work_phat_store.model.BaseEntity;
import com.example.course_work_phat_store.model.secuirty.ApplicationUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "profile_t")
@AllArgsConstructor
@NoArgsConstructor
public class Profile extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "phone") // Новое поле для хранения телефона
    private String phone;

    @Column(name = "address")
    private String address;
    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private ApplicationUser user;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private Set<Order> orders;

    public Profile(String email, ApplicationUser user) {
        this.name = "";
        this.email = email;
        this.user = user;
        this.orders = new HashSet<>();
    }

    public boolean pay(double price) {
        // логика оплаты через интерфейс платежной системы
        return true;
    }
}
