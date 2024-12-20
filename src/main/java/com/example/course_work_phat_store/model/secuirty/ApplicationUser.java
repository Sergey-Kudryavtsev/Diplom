package com.example.course_work_phat_store.model.secuirty;

import com.example.course_work_phat_store.model.BaseEntity;
import com.example.course_work_phat_store.model.entities.itemAttributes.Status;
import com.example.course_work_phat_store.model.entities.shop.Order;
import com.example.course_work_phat_store.model.entities.shop.Profile;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.processing.Pattern;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.HashSet;

import static com.example.course_work_phat_store.model.secuirty.Role.ROLE_USER;

@Getter
@Setter
@Builder
@Entity
@Table(name = "application_user_t")
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUser extends BaseEntity {
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "phone") // Новое поле для хранения телефона
    private String phone;
    @Column(name = "address") // Новое поле для хранения адреса
    private String address;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Profile profile;

    public ApplicationUser(String email, String password, String phone, String address, Role role) {
        this.username = "";
        this.password = password;
        this.role = role;
        this.phone = phone; // Сохраняем телефон
        this.address = address; // Сохраняем адрес
        this.profile = Profile.builder()
                .user(this)
                .email(email)
                .phone(phone)
                .address(address)
                .orders(new HashSet<>() {{
                    add(Order.builder()
                            .status(Status.CART)
                            .positions(new HashSet<>())
                            .build());
                }})
                .build();
    }

    public UserDetails securityUserFromEntity() {
        return new User(
                this.profile.getEmail(),
                password,
                true,
                true,
                true,
                true,
                new ArrayList<>(){{add(role);}}
        );
    }
}
