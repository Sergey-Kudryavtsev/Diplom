package com.example.course_work_phat_store.model.secuirty;

import com.example.course_work_phat_store.repositories.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImplementation implements UserDetailsService {
    private final ApplicationUserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<ApplicationUser> loadedUser = repo.findApplicationUserByProfileEmail(email);
        if (loadedUser.isPresent()) {
            return loadedUser.get().securityUserFromEntity();
        } else {
            throw new UsernameNotFoundException("Данный email не зарегистрирован: " + email);
        }
    }
}
