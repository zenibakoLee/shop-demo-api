package com.example.demo.controller;

import com.example.demo.application.GetCategoryListService;
import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.CategoryListDto;
import com.example.demo.model.Category;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final GetCategoryListService getCategoryListService;

    public CategoryController(GetCategoryListService getCategoryListService) {
        this.getCategoryListService = getCategoryListService;
    }

    @GetMapping
    public CategoryListDto list() {
//        return "{\"categories\":[]}";

//        CategoryDto categoryDto = new CategoryDto("0BV000CAT0001", "top");
//        return new CategoryListDto(List.of(categoryDto));

//        CategoryId id = new CategoryId("0BV000CAT0001");
//        Category category = new Category(id, "top");

        List<Category> categories = getCategoryListService.getCategories();
        List<CategoryDto> categoryDtos = categories.stream().map(category -> mapToDto(category)).toList();
        return new CategoryListDto(categoryDtos);
    }

    private CategoryDto mapToDto(Category category) {
        return new CategoryDto(category.id().toString(), category.name());
    }
}