package com.example.course_work_phat_store.dao.implementations;

import com.example.course_work_phat_store.dao.services.ProfileService;
import com.example.course_work_phat_store.model.entities.shop.Profile;
import com.example.course_work_phat_store.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImplementation implements ProfileService {
    private final ProfileRepository repo;

    @Override
    public List<Profile> findAll() {
        return null;
    }

    @Override
    public Optional<Profile> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Profile save(Profile profile) {
        return repo.save(profile);
    }

    @Override
    public void deleteById(int id) {

    }
}
