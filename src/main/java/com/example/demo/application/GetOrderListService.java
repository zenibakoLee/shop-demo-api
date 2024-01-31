package com.example.demo.application;

import com.example.demo.dto.OrderListDto;
import com.example.demo.dto.OrderSummaryDto;
import com.example.demo.model.Order;
import com.example.demo.model.UserId;
import com.example.demo.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetOrderListService {
    private final OrderRepository orderRepository;

    public GetOrderListService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderListDto getOrderList(UserId userId) {
        List<Order> orders =
                orderRepository.findAllByUserIdOrderByIdDesc(userId);

        return new OrderListDto(
                orders.stream()
                        .map(OrderSummaryDto::of)
                        .toList()
        );
    }
}