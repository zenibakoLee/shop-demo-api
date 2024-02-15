package com.example.demo.controller.admin;

import com.example.demo.application.LoginService;
import com.example.demo.application.LogoutService;
import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.LoginResultDto;
import com.example.demo.security.AuthUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController("AdminSessionController")
@RequestMapping("/admin/session")
public class AdminSessionController {
    private final LoginService loginService;
    private final LogoutService logoutService;

    public AdminSessionController(LoginService loginService, LogoutService logoutService) {
        this.loginService = loginService;
        this.logoutService = logoutService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResultDto login(@RequestBody LoginRequestDto loginRequestDto) {
        String accessToken = loginService.login(
                loginRequestDto.email(), loginRequestDto.password());

        return new LoginResultDto(accessToken);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String loginFailed() {
        return "Bad Request";
    }

    @DeleteMapping
    public String logout(Authentication authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();


        logoutService.logout(authUser.accessToken());

        return "Logout";
    }
}
