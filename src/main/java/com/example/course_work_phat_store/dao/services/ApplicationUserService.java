package com.example.course_work_phat_store.dao.services;

import com.example.course_work_phat_store.model.secuirty.ApplicationUser;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ApplicationUserService extends DAO<ApplicationUser> {
    ApplicationUser loadUserByUsername(String email) throws UsernameNotFoundException;
}
