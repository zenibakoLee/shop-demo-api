package com.example.demo.controller.admin;

import com.example.demo.annotation.AdminRequired;
import com.example.demo.application.GetAdminOrderDetailService;
import com.example.demo.application.GetOrderListService;
import com.example.demo.application.UpdateOrderService;
import com.example.demo.dto.AdminOrderDetailDto;
import com.example.demo.dto.AdminOrderListDto;
import com.example.demo.dto.AdminUpdateOrderDto;
import com.example.demo.model.OrderId;
import com.example.demo.model.OrderStatus;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AdminRequired
@RestController
@RequestMapping("/admin/orders")
public class AdminOrderController {
    private final GetOrderListService getOrderListService;
    private final GetAdminOrderDetailService getAdminOrderDetailService;
    private final UpdateOrderService updateOrderService;

    public AdminOrderController(GetOrderListService getOrderListService, GetAdminOrderDetailService getAdminOrderDetailService1, UpdateOrderService updateOrderService) {
        this.getOrderListService = getOrderListService;
        this.getAdminOrderDetailService = getAdminOrderDetailService1;
        this.updateOrderService = updateOrderService;
    }

    @GetMapping
    public AdminOrderListDto list() {
        return getOrderListService.getAdminOrderList();
    }

    @GetMapping("/{id}")
    public AdminOrderDetailDto detail(@PathVariable String id) {
        OrderId orderId = new OrderId(id);
        return getAdminOrderDetailService.getOrderDetail(orderId);
    }

    @PatchMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @RequestBody AdminUpdateOrderDto orderDto) {
        OrderId orderId = new OrderId(id);
        OrderStatus status = OrderStatus.of(orderDto.status());
        updateOrderService.updateOrderStatus(orderId, status);
        return "Updated";
    }
}