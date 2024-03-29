package com.example.demo.repository;

import com.example.demo.model.User;
import com.example.demo.model.UserId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, UserId> { // thanks to Spring data jpa
    Optional<User> findById(UserId userId);

    Boolean existsByEmail(String email);

    List<User> findAllByOrderByIdDesc();

    List<User> findAllByIdIn(List<UserId> userIds);
}
