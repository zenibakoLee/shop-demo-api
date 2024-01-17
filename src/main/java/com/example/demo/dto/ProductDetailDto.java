package com.example.demo.dto;

import java.util.List;

public record ProductDetailDto(String id,
                               CategoryDto category,
                               List<ImageDto> images,
                               String name,
                               Long price,
                               List<ProductOptionDto> options,
                               String description) {
}
