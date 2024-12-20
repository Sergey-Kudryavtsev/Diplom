package com.example.course_work_phat_store.repositories;


import com.example.course_work_phat_store.model.secuirty.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Integer> {
    Optional<ApplicationUser> findApplicationUserByProfileEmail(String email);
}
