package com.example.demo.security;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 주로 인증 및 보안과 관련된 작업을 수행합니다.
 * 실제 인증 작업을 위한 정보(예: 액세스 토큰)를 관리하며, 데이터베이스의 access_tokens 테이블과 매핑될 수 있습니다.
 */
@Component
public class AuthUserDao {
    private final JdbcTemplate jdbcTemplate;

    public AuthUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<AuthUser> findByEmail(String email) {
        String query = "SELECT id, password, role FROM users WHERE email=?";

        return jdbcTemplate.query(query, resultSet -> {
            if (!resultSet.next()) {
                return Optional.empty();
            }

            AuthUser authUser = AuthUser.of(
                    resultSet.getString("id"),
                    email,
                    resultSet.getString("password"),
                    resultSet.getString("role")
            );

            return Optional.of(authUser);
        }, email);
    }

    public void addAccessToken(String userId, String accessToken) {
        jdbcTemplate.update("""
                        INSERT INTO access_tokens (token, user_id)
                        VALUES (?, ?)
                        """,
                accessToken, userId
        );
    }

    public Optional<AuthUser> findByAccessToken(String accessToken) {
        String query = """
                SELECT users.id, users.role
                FROM users
                JOIN access_tokens ON access_tokens.user_id=users.id
                WHERE access_tokens.token=?
                """;

        return jdbcTemplate.query(query, resultSet -> {
            if (!resultSet.next()) {
                return Optional.empty();
            }

            AuthUser authUser = AuthUser.authenticated(
                    resultSet.getString("id"),
                    resultSet.getString("role"),
                    accessToken
            );

            return Optional.of(authUser);
        }, accessToken);
    }

    public void removeAccessToken(String accessToken) {
        jdbcTemplate.update("""
                        DELETE FROM access_tokens WHERE token=?
                        """,
                accessToken
        );
    }
}