package com.example.demo.repository;

import com.example.demo.model.Cart;
import com.example.demo.model.CartId;
import com.example.demo.model.UserId;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, CartId> {
    Optional<Cart> findByUserId(UserId userId);
}
