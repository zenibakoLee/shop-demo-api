package com.example.demo.infrastructure.dto;

public record PortonePaymentDto(
        Response response
) {
    public record Response(
            Long amount
    ) {
    }
}