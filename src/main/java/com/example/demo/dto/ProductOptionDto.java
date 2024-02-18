package com.example.demo.dto;

import com.example.demo.model.ProductOption;

import java.util.List;
import java.util.stream.Collectors;

public record ProductOptionDto(String id,
                               String name,
                               List<ProductOptionItemDto> items) {

    public static ProductOptionDto of(ProductOption option) {
        return new ProductOptionDto(
                option.id().toString(),
                option.name(),
                option.items().stream()
                        .map(ProductOptionItemDto::of) // Convert ProductOptionItem to ProductOptionItemDto
                        .collect(Collectors.toList())
        );
    }
}
