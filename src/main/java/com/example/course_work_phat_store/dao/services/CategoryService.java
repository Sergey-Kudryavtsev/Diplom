package com.example.course_work_phat_store.dao.services;


import com.example.course_work_phat_store.model.entities.stock.entities.Category;

public interface CategoryService extends DAO<Category> {
    void addCategory(String name);

    void deleteCategory(int categoryId);

//    void editCategory(int categoryId, String newName);
}
