package com.example.demo.dto;

import java.util.List;

public record AdminOrderListDto(
        List<AdminOrderSummaryDto> orders
) {
}