package com.example.demo.application;

import com.example.demo.exception.EmailAlreadyTaken;
import com.example.demo.model.User;
import com.example.demo.model.UserId;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.AccessTokenGenerator;
import com.example.demo.security.AuthUserDao;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.demo.model.Role.ROLE_USER;

@Service
@Transactional
public class SignupService {
    private final AuthUserDao authUserDao;

    private final PasswordEncoder passwordEncoder;

    private final AccessTokenGenerator accessTokenGenerator;

    private final UserRepository userRepository;

    public SignupService(AuthUserDao authUserDao,
                         PasswordEncoder passwordEncoder,
                         AccessTokenGenerator accessTokenGenerator,
                         UserRepository userRepository) {
        this.authUserDao = authUserDao;
        this.passwordEncoder = passwordEncoder;
        this.accessTokenGenerator = accessTokenGenerator;
        this.userRepository = userRepository;
    }

    public String signup(String email, String name, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyTaken(email);
        }

        UserId userId = createUser(email, name, password);

        return createAccessToken(userId);
    }

    private UserId createUser(String email, String name, String password) {
        UserId userId = UserId.generate();

        User user = new User(userId, email, name, ROLE_USER);
        user.changePassword(password, passwordEncoder);

        userRepository.save(user);

        return userId;
    }

    private String createAccessToken(UserId userId) {
        String accessToken = accessTokenGenerator.generate(userId.toString());

        authUserDao.addAccessToken(userId.toString(), accessToken);

        return accessToken;
    }
}