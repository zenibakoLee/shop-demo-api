package com.example.demo.application;

import com.example.demo.Fixtures;
import com.example.demo.dto.AdminOrderDetailDto;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetAdminOrderDetailServiceTest {
    private OrderRepository orderRepository;
    private UserRepository userRepository;

    private GetAdminOrderDetailService getAdminOrderDetailService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        userRepository = mock(UserRepository.class);

        getAdminOrderDetailService = new GetAdminOrderDetailService(
                orderRepository, userRepository);
    }

    @Test
    void getOrderDetail() {
        User user = Fixtures.user("tester");
        Order order = Fixtures.order(user);

        given(orderRepository.findById(order.id()))
                .willReturn(Optional.of(order));
        given(userRepository.findById(user.id()))
                .willReturn(Optional.of(user));

        AdminOrderDetailDto orderDto =
                getAdminOrderDetailService.getOrderDetail(order.id());

        assertThat(orderDto.lineItems()).hasSize(1);
    }
}