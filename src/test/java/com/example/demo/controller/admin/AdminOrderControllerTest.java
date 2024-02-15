package com.example.demo.controller.admin;

import com.example.demo.Fixtures;
import com.example.demo.application.GetAdminOrderDetailService;
import com.example.demo.application.GetOrderListService;
import com.example.demo.application.UpdateOrderService;
import com.example.demo.controller.ControllerTest;
import com.example.demo.model.Order;
import com.example.demo.model.OrderId;
import com.example.demo.model.OrderStatus;
import com.example.demo.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminOrderController.class)
class AdminOrderControllerTest extends ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetOrderListService getOrderListService;

    @MockBean
    private GetAdminOrderDetailService getAdminOrderDetailService;

    @MockBean
    private UpdateOrderService updateOrderService;

    @Test
    @DisplayName("GET /admin/orders")
    void list() throws Exception {
        mockMvc.perform(get("/admin/orders")
                        .header("Authorization", "Bearer " + adminAccessToken))
                .andExpect(status().isOk());

        verify(getOrderListService).getAdminOrderList();
    }

    @Test
    @DisplayName("GET /admin/orders/{id}")
    void detail() throws Exception {
        User user = Fixtures.user("tester");
        Order order = Fixtures.order(user);

        OrderId orderId = order.id();

        mockMvc.perform(get("/admin/orders/" + orderId)
                        .header("Authorization", "Bearer " + adminAccessToken))
                .andExpect(status().isOk());

        verify(getAdminOrderDetailService).getOrderDetail(orderId);
    }

    @Test
    @DisplayName("PATCH /admin/orders/{id}")
    void update() throws Exception {
        User user = Fixtures.user("tester");
        Order order = Fixtures.order(user);

        String json = """
                {
                    "status": "ready"
                }
                """;

        OrderId orderId = order.id();

        mockMvc.perform(patch("/admin/orders/" + orderId)
                        .header("Authorization", "Bearer " + adminAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(updateOrderService)
                .updateOrderStatus(orderId, OrderStatus.READY);
    }
}