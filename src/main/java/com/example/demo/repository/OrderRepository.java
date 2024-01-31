package com.example.demo.repository;

import com.example.demo.model.Order;
import com.example.demo.model.OrderId;
import com.example.demo.model.UserId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, OrderId> {
    List<Order> findAllByUserIdOrderByIdDesc(UserId userId);

    Optional<Order> findByIdAndUserId(OrderId orderId, UserId userId);
}
