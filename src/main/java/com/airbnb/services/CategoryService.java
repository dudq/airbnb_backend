package com.airbnb.services;

import com.airbnb.models.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> findAll();

    Category findByName(String name);

    Category findById(Long id);

    void createCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(Long id);
}
