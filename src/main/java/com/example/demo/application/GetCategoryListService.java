package com.example.demo.application;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetCategoryListService {
    private final CategoryRepository categoryRepository;

    public GetCategoryListService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        // TODO: 가짜 데이터. 진짜로 바꿔줄 것!
//        CategoryId id = new CategoryId("0BV000CAT0001");
//        Category category = new Category(id, "top");
//        return List.of(category);

        return categoryRepository.findAll();


    }
}
