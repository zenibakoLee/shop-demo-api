package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record AdminUpdateOrderDto(
        @NotBlank
        String status
) {
}