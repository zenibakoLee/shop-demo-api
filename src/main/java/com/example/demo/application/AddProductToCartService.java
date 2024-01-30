package com.example.demo.application;

import com.example.demo.model.Cart;
import com.example.demo.model.CartId;
import com.example.demo.model.CartLineItemOption;
import com.example.demo.model.Product;
import com.example.demo.model.ProductId;
import com.example.demo.model.UserId;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Transactional
public class AddProductToCartService {
    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    public AddProductToCartService(CartRepository cartRepository,
                                   ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public void addProductToCart(UserId userId, ProductId productId,
                                 Set<CartLineItemOption> options, int quantity) {
        // 장바구니가 없으면 장바구니 객체를 만들고, 나중에 save를 해준다.
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(new Cart(CartId.generate(), userId));

        Product product = productRepository.findById(productId)
                .orElseThrow();

        cart.addProduct(product, options, quantity);

        cartRepository.save(cart);
    }
}