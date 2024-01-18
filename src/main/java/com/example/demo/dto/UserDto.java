package com.example.demo.dto;

import com.example.demo.model.User;

public record UserDto(
        String id,
        String name
) {
    public static UserDto of(User user) {
        return new UserDto(
                user.id().toString(),
                user.name()
        );
    }
}
