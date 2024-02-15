package com.example.demo.controller.admin;

import com.example.demo.annotation.AdminRequired;
import com.example.demo.application.CreateCategoryService;
import com.example.demo.application.GetCategoryDetailService;
import com.example.demo.application.GetCategoryListService;
import com.example.demo.application.UpdateCategoryService;
import com.example.demo.dto.AdminCategoryDetailDto;
import com.example.demo.dto.AdminCategoryListDto;
import com.example.demo.dto.AdminCreateCategoryDto;
import com.example.demo.dto.AdminUpdateCategoryDto;
import com.example.demo.model.Category;
import com.example.demo.model.CategoryId;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AdminRequired
@RestController
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private final GetCategoryListService getCategoryListService;
    private final GetCategoryDetailService getCategoryDetailService;
    private final CreateCategoryService createCategoryService;
    private final UpdateCategoryService updateCategoryService;

    public AdminCategoryController(
            GetCategoryListService getCategoryListService, GetCategoryDetailService getCategoryDetailService, CreateCategoryService createCategoryService, UpdateCategoryService updateCategoryService) {
        this.getCategoryListService = getCategoryListService;
        this.getCategoryDetailService = getCategoryDetailService;
        this.createCategoryService = createCategoryService;
        this.updateCategoryService = updateCategoryService;
    }

    @GetMapping
    public AdminCategoryListDto list() {
        List<Category> categories = getCategoryListService.getAllCategories();
        return AdminCategoryListDto.of(categories);
    }

    @GetMapping("/{id}")
    public AdminCategoryDetailDto detail(@PathVariable String id) {
        CategoryId categoryId = new CategoryId(id);
        Category category = getCategoryDetailService.getCategory(categoryId);
        return AdminCategoryDetailDto.of(category);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(
            @Valid @RequestBody AdminCreateCategoryDto categoryDto) {
        createCategoryService.createCategory(categoryDto.name());
        return "Created";
    }

    @PatchMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @RequestBody AdminUpdateCategoryDto categoryDto) {
        updateCategoryService.updateCategory(
                new CategoryId(id), categoryDto.name(), categoryDto.hidden());
        return "Updated";
    }
}