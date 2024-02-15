package com.example.demo.application;

import com.example.demo.model.Category;
import com.example.demo.model.CategoryId;
import com.example.demo.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetCategoryDetailService {
    private final CategoryRepository categoryRepository;

    public GetCategoryDetailService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategory(CategoryId categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow();
    }
}