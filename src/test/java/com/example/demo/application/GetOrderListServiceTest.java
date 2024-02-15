package com.example.demo.application;

import com.example.demo.Fixtures;
import com.example.demo.dto.AdminOrderListDto;
import com.example.demo.dto.OrderListDto;
import com.example.demo.model.Order;
import com.example.demo.model.OrderId;
import com.example.demo.model.OrderLineItem;
import com.example.demo.model.OrderLineItemId;
import com.example.demo.model.OrderStatus;
import com.example.demo.model.Payment;
import com.example.demo.model.Product;
import com.example.demo.model.Receiver;
import com.example.demo.model.User;
import com.example.demo.model.UserId;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.demo.TestUtils.createOrderOptions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetOrderListServiceTest {
    private OrderRepository orderRepository;

    private GetOrderListService getOrderListService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        userRepository = mock(UserRepository.class);
        getOrderListService = new GetOrderListService(orderRepository, userRepository);
    }

    @Test
    void getOrderList() {
        UserId userId = UserId.generate();

        Product product = Fixtures.product("맨투맨");

        List<OrderLineItem> lineItems = List.of(
                new OrderLineItem(
                        OrderLineItemId.generate(),
                        product,
                        createOrderOptions(product, new int[]{0, 0}),
                        1
                )
        );

        Receiver receiver = Fixtures.receiver("홍길동");
        Payment payment = Fixtures.payment();

        Order order = new Order(OrderId.generate(), UserId.generate(),
                lineItems, receiver, payment, OrderStatus.PAID);

        given(orderRepository.findAllByUserIdOrderByIdDesc(userId))
                .willReturn(List.of(order));

        OrderListDto orderListDto = getOrderListService.getOrderList(userId);

        assertThat(orderListDto.orders()).hasSize(1);
    }

    @Test
    void getAdminOrderList() {
        User user = Fixtures.user("tester");
        Order order = Fixtures.order(user);

        given(orderRepository.findAllByOrderByIdDesc())
                .willReturn(List.of(order));
        given(userRepository.findAllByIdIn(List.of(user.id())))
                .willReturn(List.of(user));

        AdminOrderListDto ordersDto = getOrderListService.getAdminOrderList();

        assertThat(ordersDto.orders()).hasSize(1);
    }
}