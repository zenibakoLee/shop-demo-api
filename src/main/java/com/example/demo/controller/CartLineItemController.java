package com.example.demo.controller;

import com.example.demo.annotation.UserRequired;
import com.example.demo.application.AddProductToCartService;
import com.example.demo.dto.AddProductToCartDto;
import com.example.demo.model.CartLineItemOption;
import com.example.demo.model.ProductId;
import com.example.demo.model.ProductOptionId;
import com.example.demo.model.ProductOptionItemId;
import com.example.demo.model.UserId;
import com.example.demo.security.AuthUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@UserRequired
@RestController
@RequestMapping("/cart/line-items")
public class CartLineItemController {
    private final AddProductToCartService addProductToCartService;

    public CartLineItemController(
            AddProductToCartService addProductToCartService) {
        this.addProductToCartService = addProductToCartService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(
            Authentication authentication,
            @Valid @RequestBody AddProductToCartDto requestDto) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        UserId userId = new UserId(authUser.id());
        ProductId productId = new ProductId(requestDto.productId());
        Set<CartLineItemOption> options = requestDto.options().stream()
                .map(option -> new CartLineItemOption(
                        new ProductOptionId(option.id()),
                        new ProductOptionItemId(option.itemId())
                ))
                .collect(Collectors.toSet());
        int quantity = requestDto.quantity();

        addProductToCartService.addProductToCart(
                userId, productId, options, quantity);

        return "Created";
    }
}