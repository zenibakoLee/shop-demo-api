package com.example.demo.application;

import com.example.demo.Fixtures;
import com.example.demo.dto.OrderDetailDto;
import com.example.demo.model.Order;
import com.example.demo.model.OrderId;
import com.example.demo.model.OrderLineItem;
import com.example.demo.model.OrderLineItemId;
import com.example.demo.model.OrderStatus;
import com.example.demo.model.Payment;
import com.example.demo.model.Product;
import com.example.demo.model.Receiver;
import com.example.demo.model.UserId;
import com.example.demo.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.example.demo.TestUtils.createOrderOptions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetOrderDetailServiceTest {
    private OrderRepository orderRepository;

    private GetOrderDetailService getOrderDetailService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);

        getOrderDetailService = new GetOrderDetailService(orderRepository);
    }

    @Test
    void getOrderDetail() {
        UserId userId = UserId.generate();
        OrderId orderId = OrderId.generate();

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

        given(orderRepository.findByIdAndUserId(orderId, userId))
                .willReturn(Optional.of(order));

        OrderDetailDto orderDetailDto =
                getOrderDetailService.getOrderDetail(orderId, userId);

        assertThat(orderDetailDto.lineItems()).hasSize(1);
    }
}