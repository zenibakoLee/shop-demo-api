package com.example.demo.controller;

import com.example.demo.application.GetUserService;
import com.example.demo.model.User;
import com.example.demo.model.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.demo.model.Role.ROLE_USER;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest extends ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetUserService getUserService;

    @Test
    @DisplayName("GET /users/me")
    void detail() throws Exception {
        UserId userId = new UserId(USER_ID);

        User user = new User(userId, "tester@example.com", "Tester", ROLE_USER);

        given(getUserService.getUser(userId)).willReturn(user);

        mockMvc.perform(get("/users/me")
                        .header("Authorization", "Bearer " + userAccessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("name")));
    }
}