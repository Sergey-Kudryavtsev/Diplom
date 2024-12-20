package com.example.course_work_phat_store.dao.implementations;

import com.example.course_work_phat_store.dao.services.ApplicationUserService;
import com.example.course_work_phat_store.model.secuirty.ApplicationUser;
import com.example.course_work_phat_store.repositories.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationUserServiceImplementation implements ApplicationUserService {
    private final ApplicationUserRepository repo;


    @Override
    public List<ApplicationUser> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<ApplicationUser> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public ApplicationUser save(ApplicationUser applicationUser) {
        return repo.save(applicationUser);
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public ApplicationUser loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<ApplicationUser> loadedUser = repo.findApplicationUserByProfileEmail(email);
        if (loadedUser.isPresent()) {
            return loadedUser.get();
        } else {
            throw new UsernameNotFoundException("Данный email не зарегистрирован: " + email);
        }
    }
}
