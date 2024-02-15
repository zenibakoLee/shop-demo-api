package com.example.demo.application;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetUserListService {
    private final UserRepository userRepository;

    public GetUserListService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUserList() {
        return userRepository.findAllByOrderByIdDesc();
    }
}