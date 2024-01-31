package com.example.demo.application;

import com.example.demo.infrastructure.PaymentValidator;
import com.example.demo.model.Cart;
import com.example.demo.model.CartLineItem;
import com.example.demo.model.Order;
import com.example.demo.model.OrderId;
import com.example.demo.model.OrderLineItem;
import com.example.demo.model.OrderLineItemId;
import com.example.demo.model.OrderOption;
import com.example.demo.model.OrderOptionId;
import com.example.demo.model.OrderStatus;
import com.example.demo.model.Payment;
import com.example.demo.model.Product;
import com.example.demo.model.ProductOption;
import com.example.demo.model.ProductOptionId;
import com.example.demo.model.ProductOptionItem;
import com.example.demo.model.ProductOptionItemId;
import com.example.demo.model.Receiver;
import com.example.demo.model.UserId;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@Service
@Transactional
public class CreateOrderService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final PaymentValidator paymentValidator;

    public CreateOrderService(ProductRepository productRepository,
                              CartRepository cartRepository,
                              OrderRepository orderRepository, PaymentValidator paymentValidator) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.paymentValidator = paymentValidator;
    }

    public Order createOrder(
            UserId userId, Receiver receiver, Payment payment) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow();

        List<OrderLineItem> lineItems = IntStream
                .range(0, cart.lineItemSize())
                .mapToObj(cart::lineItem)
                .map(this::createOrderLineItem)
                .toList();

        OrderId orderId = OrderId.generate();

        Order order = new Order(orderId, userId, lineItems, receiver, payment,
                OrderStatus.PAID);
        paymentValidator.validate(payment, order);
        orderRepository.save(order);

        cart.clear();

        return order;
    }

    private OrderLineItem createOrderLineItem(CartLineItem cartLineItem) {
        Product product = productRepository.findById(cartLineItem.productId())
                .orElseThrow();

        List<OrderOption> options = cartLineItem.optionIds().stream()
                .map(optionId ->
                        createOrderOption(cartLineItem, product, optionId))
                .toList();

        return new OrderLineItem(
                OrderLineItemId.generate(),
                product,
                options,
                cartLineItem.quantity()
        );
    }

    private static OrderOption createOrderOption(
            CartLineItem cartLineItem, Product product,
            ProductOptionId optionId) {
        ProductOptionItemId itemId = cartLineItem.optionItemId(optionId);

        ProductOption productOption = product.optionById(optionId);
        ProductOptionItem productOptionItem = productOption.itemById(itemId);

        return new OrderOption(
                OrderOptionId.generate(),
                productOption,
                productOptionItem
        );
    }
}