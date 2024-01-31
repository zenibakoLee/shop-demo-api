package com.example.demo.dto;

import java.util.List;

public record OrderListDto(
        List<OrderSummaryDto> orders
) {
}