package com.example.demo.application;

import com.example.demo.security.AuthUserDao;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LogoutService {
    private final AuthUserDao authUserDao;

    public LogoutService(AuthUserDao authUserDao) {
        this.authUserDao = authUserDao;
    }

    public void logout(String accessToken) {
        authUserDao.removeAccessToken(accessToken);
    }
}