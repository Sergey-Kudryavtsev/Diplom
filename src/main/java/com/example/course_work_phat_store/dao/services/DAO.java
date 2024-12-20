package com.example.course_work_phat_store.dao.services;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    List<T> findAll();
    Optional<T> findById(int id);
    T save(T t);
    void deleteById(int id);
}
