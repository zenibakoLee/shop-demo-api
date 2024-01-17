package com.example.demo.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

/*
Service라고 모두 Application Layer에 속하는 게 아니란 점에 주의!
 */
@Component
public class AccessTokenService {
    private final AccessTokenGenerator accessTokenGenerator;
    private final AuthUserDao authUserDao;

    public AccessTokenService(AccessTokenGenerator accessTokenGenerator,
                              AuthUserDao authUserDao) {
        this.accessTokenGenerator = accessTokenGenerator;
        this.authUserDao = authUserDao;
    }

    public Authentication authenticate(String accessToken) {
        if (!accessTokenGenerator.verify(accessToken)) {
            return null;
        }

        return authUserDao.findByAccessToken(accessToken)
                .map(authUser ->
                        UsernamePasswordAuthenticationToken.authenticated(
                                // Principal로 AuthUser 객체를 그대로 활용한다.
                                authUser, null, List.of(authUser::role)))
                .orElse(null);
    }
}
