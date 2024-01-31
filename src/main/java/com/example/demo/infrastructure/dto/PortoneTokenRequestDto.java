package com.example.demo.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PortoneTokenRequestDto(
        @JsonProperty("imp_key")
        String key,

        @JsonProperty("imp_secret")
        String secret
) {
}