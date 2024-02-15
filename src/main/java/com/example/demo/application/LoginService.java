package com.example.demo.application;

import com.example.demo.security.AccessTokenGenerator;
import com.example.demo.security.AuthUser;
import com.example.demo.security.AuthUserDao;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LoginService {
    /*
    일단 Happy Path부터 처리하자. 나머지는 모두 주석으로 막아도 된다. 일단 여기선 그냥 바로 구현을 해보자.
     */
    private final AuthUserDao authUserDao;

    private final PasswordEncoder passwordEncoder;

    private final AccessTokenGenerator accessTokenGenerator;

    public LoginService(AuthUserDao authUserDao, PasswordEncoder passwordEncoder, AccessTokenGenerator accessTokenGenerator) {
        this.authUserDao = authUserDao;
        this.passwordEncoder = passwordEncoder;
        this.accessTokenGenerator = accessTokenGenerator;
    }

    public String login(String email, String password) {
        return authUserDao.findByEmail(email)
//                .map(authUser -> {
//                    String accessToken = "Tester.Access.Token";
//                    return accessToken;
//                })
//                .get();
                .filter(authUser ->
                        passwordEncoder.matches(password, authUser.password()))
//                .map(authUser -> {
//                    String id = authUser.id();
//                    String accessToken = accessTokenGenerator.generate(id);
//                    authUserDao.addAccessToken(id, accessToken);
//                    return accessToken;
//                })
                .map(this::generateAccessToken)
                .orElseThrow(() -> new BadCredentialsException("Login failed"));
    }

    public String loginAdmin(String email, String password) {
        return authUserDao.findByEmail(email)
                .filter(authUser -> matchPassword(authUser, password))
                .filter(AuthUser::isAdmin)
                .map(this::generateAccessToken)
                .orElseThrow(() -> new BadCredentialsException("Login failed"));
    }

    private boolean matchPassword(AuthUser authUser, String password) {
        return passwordEncoder.matches(password, authUser.password());
    }

    private String generateAccessToken(AuthUser authUser) {
        String id = authUser.id();
        String accessToken = accessTokenGenerator.generate(id);
        authUserDao.addAccessToken(id, accessToken);
        return accessToken;
    }
}
