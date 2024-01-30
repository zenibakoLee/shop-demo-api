package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignupRequestDto(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String name,
        @NotBlank
        String password
) {
}
