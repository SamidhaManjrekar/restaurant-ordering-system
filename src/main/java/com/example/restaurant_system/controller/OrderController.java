package com.example.restaurant_system.controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurant_system.dto.OrderDTO.*;
import com.example.restaurant_system.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    public OrderController(OrderService orderService) { this.orderService = orderService; }

    // 1. Place order (Customer)
    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody PlaceOrderRequest req) {
        OrderResponse res = orderService.placeOrder(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    // 2. Cancel order (Customer)
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId, @RequestParam Long userId) {
        OrderResponse res = orderService.cancelOrder(orderId, userId);
        return ResponseEntity.ok(res);
    }

    // 3. View order history (Customer)
    @GetMapping("/customer/{userId}")
    public ResponseEntity<List<OrderResponse>> getMyOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomer(userId));
    }

    // 4. View all active orders (Staff)
    @GetMapping("/active")
    public ResponseEntity<List<OrderResponse>> getActiveOrders() {
        return ResponseEntity.ok(orderService.getActiveOrders());
    }

    // 5. Update order status (Staff)
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable Long orderId, @RequestBody UpdateStatusRequest req) {
        OrderResponse res = orderService.updateOrderStatus(orderId, req);
        return ResponseEntity.ok(res);
    }
}
