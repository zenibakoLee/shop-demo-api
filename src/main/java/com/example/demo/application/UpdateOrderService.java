package com.example.demo.application;

import com.example.demo.model.Order;
import com.example.demo.model.OrderId;
import com.example.demo.model.OrderStatus;
import com.example.demo.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateOrderService {
    private final OrderRepository orderRepository;

    public UpdateOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void updateOrderStatus(OrderId orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow();

        order.changeStatus(status);
    }
}