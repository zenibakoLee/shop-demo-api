package com.example.demo.application;

import com.example.demo.model.Category;
import com.example.demo.model.CategoryId;
import com.example.demo.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateCategoryService {
    private final CategoryRepository categoryRepository;

    public UpdateCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void updateCategory(CategoryId id, String name, boolean hidden) {
        Category category = categoryRepository.findById(id)
                .orElseThrow();

        category.changeName(name);

        if (hidden) {
            category.hide();
            return;
        }

        category.show();
    }
}