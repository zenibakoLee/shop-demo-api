package com.example.demo.application;

import com.example.demo.dto.CartDto;
import com.example.demo.model.Cart;
import com.example.demo.model.CartLineItem;
import com.example.demo.model.Money;
import com.example.demo.model.Product;
import com.example.demo.model.ProductId;
import com.example.demo.model.ProductOption;
import com.example.demo.model.ProductOptionId;
import com.example.demo.model.ProductOptionItem;
import com.example.demo.model.ProductOptionItemId;
import com.example.demo.model.UserId;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
public class GetCartService {
    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    public GetCartService(CartRepository cartRepository,
                          ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public CartDto getCartDto(UserId userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(new Cart(userId));

        List<CartDto.LineItem> lineItems = lineItemDtos(cart);

        long totalPrice = lineItems.stream()
                .mapToLong(CartDto.LineItem::totalPrice)
                .sum();

        return new CartDto(
                lineItems,
                totalPrice
        );
    }

    private List<CartDto.LineItem> lineItemDtos(Cart cart) {
        return IntStream.range(0, cart.lineItemSize())
                .mapToObj(index -> {
                    CartLineItem lineItem = cart.lineItem(index);
                    return lineItemDto(lineItem);
                })
                .toList();
    }

    private CartDto.LineItem lineItemDto(CartLineItem lineItem) {
        ProductId productId = lineItem.productId();

        Product product = productRepository.findById(productId)
                .orElseThrow();

        Money unitPrice = product.price();
        int quantity = lineItem.quantity();

        return new CartDto.LineItem(
                lineItem.id().toString(),
                productDto(product),
                optionDtos(lineItem, product),
                unitPrice.asLong(),
                quantity,
                unitPrice.times(quantity).asLong()
        );
    }

    private CartDto.Product productDto(Product product) {
        return new CartDto.Product(
                product.id().toString(),
                new CartDto.Image(
                        product.image(0).url()
                ),
                product.name()
        );
    }

    private List<CartDto.Option> optionDtos(CartLineItem lineItem,
                                            Product product) {
        return lineItem.optionIds().stream()
                .map(optionId -> optionDto(lineItem, product, optionId))
                .toList();
    }

    private CartDto.Option optionDto(CartLineItem lineItem, Product product,
                                     ProductOptionId optionId) {
        ProductOption option = product.optionById(optionId);
        ProductOptionItemId itemId = lineItem.optionItemId(optionId);
        ProductOptionItem optionItem = option.itemById(itemId);

        return new CartDto.Option(
                option.name(),
                new CartDto.OptionItem(
                        optionItem.name()
                )
        );
    }
}