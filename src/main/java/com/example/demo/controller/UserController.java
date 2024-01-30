package com.example.demo.controller;

import com.example.demo.application.GetUserService;
import com.example.demo.application.SignupService;
import com.example.demo.dto.SignupRequestDto;
import com.example.demo.dto.SignupResultDto;
import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.model.UserId;
import com.example.demo.security.AuthUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final GetUserService getUserService;
    private SignupService signupService;

    public UserController(GetUserService getUserService, SignupService signupService) {
        this.getUserService = getUserService;
        this.signupService = signupService;
    }

    @GetMapping("/me")
    public UserDto me(Authentication authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        UserId id = new UserId(authUser.id());
        User user = getUserService.getUser(id);
        return UserDto.of(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SignupResultDto signup(
            @Valid @RequestBody SignupRequestDto signupRequestDto
    ) {
        String accessToken = signupService.signup(
                signupRequestDto.email().trim(),
                signupRequestDto.name().trim(),
                signupRequestDto.password().trim()
        );

        return new SignupResultDto(accessToken);
    }
}