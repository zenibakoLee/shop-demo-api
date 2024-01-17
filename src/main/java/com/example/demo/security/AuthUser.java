package com.example.demo.security;

/*
DTO 에 가깝다
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
}