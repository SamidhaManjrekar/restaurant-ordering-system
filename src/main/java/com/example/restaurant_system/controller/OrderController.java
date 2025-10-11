package com.example.restaurant_system.controller;

import com.example.restaurant_system.dto.OrderDTO;
import com.example.restaurant_system.dto.OrderDTO.*;
import com.example.restaurant_system.entity.OrderStatus;
import com.example.restaurant_system.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Place order (Customer)
    @PostMapping("/place")
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody PlaceOrderRequest request) {
        OrderDTO order = orderService.placeOrder(request);
        return ResponseEntity.ok(order);
    }

    // Cancel order (Customer)
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderDTO> cancelOrder(
            @PathVariable Long orderId,
            @RequestParam Long customerId) {
        OrderDTO cancelledOrder = orderService.cancelOrder(orderId, customerId);
        return ResponseEntity.ok(cancelledOrder);
    }

    // View order history (Customer)
    @GetMapping("/history")
    public ResponseEntity<List<OrderDTO>> getOrderHistory(@RequestParam Long customerId) {
        List<OrderDTO> orders = orderService.getOrderHistory(customerId);
        return ResponseEntity.ok(orders);
    }

    // View all active orders (Staff)
    @GetMapping("/active")
    public ResponseEntity<List<OrderDTO>> getAllActiveOrders() {
        List<OrderDTO> activeOrders = orderService.getAllActiveOrders();
        return ResponseEntity.ok(activeOrders);
    }

    // Update order status (Staff)
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {
        OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }
}