package com.example.demo.dto;

import java.util.List;

public record ProductOptionDto(String id,
                               String name,
                               List<ProductOptionItemDto> items) {
}
