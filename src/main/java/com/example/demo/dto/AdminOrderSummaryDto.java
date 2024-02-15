package com.example.demo.dto;

import com.example.demo.model.Order;
import com.example.demo.model.User;

import java.time.format.DateTimeFormatter;

public record AdminOrderSummaryDto(
        String id,
        OrdererDto orderer,
        String title,
        Long totalPrice,
        String status,
        String orderedAt
) {
    private final static DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static AdminOrderSummaryDto of(Order order, User user) {
        return new AdminOrderSummaryDto(
                order.id().toString(),
                new OrdererDto(user.name()),
                order.title(),
                order.totalPrice().asLong(),
                order.status().toString(),
                order.orderedAt().format(DATE_TIME_FORMATTER)
        );
    }

    public record OrdererDto(
            String name
    ) {
    }
}