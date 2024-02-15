package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record AdminCreateCategoryDto(
        @NotBlank
        String name
) {
}
