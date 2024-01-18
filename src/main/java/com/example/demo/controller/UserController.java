package com.example.demo.controller;

import com.example.demo.application.GetUserService;
import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.model.UserId;
import com.example.demo.security.AuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final GetUserService getUserService;

    public UserController(GetUserService getUserService) {
        this.getUserService = getUserService;
    }

    @GetMapping("/me")
    public UserDto me(Authentication authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        UserId id = new UserId(authUser.id());
        User user = getUserService.getUser(id);
        return UserDto.of(user);
    }
}