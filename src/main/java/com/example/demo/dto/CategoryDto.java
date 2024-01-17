package com.example.demo.dto;

import com.example.demo.model.Category;

public record CategoryDto(String id,
                          String name) {

    public static CategoryDto of(Category category) {
        return new CategoryDto(category.id().toString(), category.name());
    }
}
