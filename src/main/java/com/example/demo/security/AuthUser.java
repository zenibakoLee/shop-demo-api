package com.example.demo.security;

import com.example.demo.model.Role;

/*
DTO 에 가까움.
AuthUser는 사용자를 나타내지만, 주로 인증된 사용자에 대한 정보를 포함합니다.
 */
public record AuthUser(
        String id,
        String email,
        String password,
        String role,
        String accessToken
) {
    public static AuthUser of( // login 정보 의존
                               String id, String email, String password, String role) {
        return new AuthUser(id, email, password, role, "");
    }

    public static AuthUser authenticated( // accessToken 의존
                                          String id, String role, String accessToken) {
        return new AuthUser(id, "", "", role, accessToken);
    }

    public boolean isAdmin() {
        return Role.valueOf(role).equals(Role.ROLE_ADMIN);
    }
}