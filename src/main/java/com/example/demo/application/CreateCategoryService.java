package com.example.demo.application;

import com.example.demo.model.Category;
import com.example.demo.model.CategoryId;
import com.example.demo.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateCategoryService {
    private final CategoryRepository categoryRepository;

    public CreateCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(String name) {
        Category category = new Category(CategoryId.generate(), name, true);

        categoryRepository.save(category);
    }
}