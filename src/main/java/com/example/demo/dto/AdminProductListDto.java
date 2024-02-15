package com.example.demo.dto;

import java.util.List;

public record AdminProductListDto(
        List<AdminProductSummaryDto> products
) {
}