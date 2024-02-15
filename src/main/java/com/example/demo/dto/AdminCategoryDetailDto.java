package com.example.demo.dto;

import com.example.demo.model.Category;

public record AdminCategoryDetailDto(
        String id,
        String name,
        boolean hidden
) {
    public static AdminCategoryDetailDto of(Category category) {
        return new AdminCategoryDetailDto(
                category.id().toString(),
                category.name(),
                category.hidden()
        );
    }
}