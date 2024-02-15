package com.example.demo.dto;

import com.example.demo.model.Category;
import com.example.demo.model.Product;

public record AdminProductSummaryDto(
        String id,
        CategoryDto category,
        ImageDto thumbnail,
        String name,
        Long price,
        boolean hidden
) {
    public static AdminProductSummaryDto of(
            Product product, Category category) {
        return new AdminProductSummaryDto(
                product.id().toString(),
                CategoryDto.of(category),
                ImageDto.of(product.image(0)),
                product.name(),
                product.price().asLong(),
                product.hidden()
        );
    }
}