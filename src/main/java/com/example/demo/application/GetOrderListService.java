package com.example.demo.application;

import com.example.demo.dto.AdminOrderListDto;
import com.example.demo.dto.AdminOrderSummaryDto;
import com.example.demo.dto.OrderListDto;
import com.example.demo.dto.OrderSummaryDto;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.model.UserId;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetOrderListService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public GetOrderListService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
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

    public AdminOrderListDto getAdminOrderList() {
        List<Order> orders = orderRepository.findAllByOrderByIdDesc();
        List<UserId> userIds = orders.stream()
                .map(Order::userId)
                .toList();
        List<User> users = userRepository.findAllByIdIn(userIds);

        return new AdminOrderListDto(
                orders.stream()
                        .map(order -> {
                            User user = users.stream()
                                    .filter(u -> u.id().equals(order.userId()))
                                    .findFirst()
                                    .orElseThrow();
                            return AdminOrderSummaryDto.of(order, user);
                        })
                        .toList()
        );
    }
}