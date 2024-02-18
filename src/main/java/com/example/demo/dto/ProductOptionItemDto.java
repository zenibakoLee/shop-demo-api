package com.example.demo.dto;

import com.example.demo.model.ProductOptionItem;

public record ProductOptionItemDto(String id,
                                   String name) {
    public static ProductOptionItemDto of(ProductOptionItem productOptionItem) {
        return new ProductOptionItemDto(
                productOptionItem.id().toString(),
                productOptionItem.name()
        );
    }
}
