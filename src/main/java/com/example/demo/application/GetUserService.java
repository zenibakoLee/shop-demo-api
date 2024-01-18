package com.example.demo.application;

import com.example.demo.model.User;
import com.example.demo.model.UserId;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetUserService {
    private final UserRepository userRepository;

    public GetUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(UserId id) {
        return userRepository.findById(id)
                .orElseThrow();
    }
}
