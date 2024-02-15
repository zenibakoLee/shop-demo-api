package com.example.demo.application;

import com.example.demo.Fixtures;
import com.example.demo.model.Order;
import com.example.demo.model.OrderId;
import com.example.demo.model.OrderStatus;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UpdateOrderServiceTest {
    private OrderRepository orderRepository;

    private UpdateOrderService updateOrderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);

        updateOrderService = new UpdateOrderService(orderRepository);
    }

    @Test
    void updateOrderStatus() {
        User user = Fixtures.user("tester");
        Order order = Fixtures.order(user);

        OrderId orderId = order.id();
        OrderStatus newStatus = OrderStatus.COMPLETE;

        given(orderRepository.findById(orderId)).willReturn(Optional.of(order));

        assertThat(order.status()).isNotEqualTo(newStatus);

        updateOrderService.updateOrderStatus(orderId, newStatus);

        assertThat(order.status()).isEqualTo(newStatus);
    }
}