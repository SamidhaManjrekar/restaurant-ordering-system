package com.example.restaurant_system.service;

import com.example.restaurant_system.dto.OrderDTO.*;
import com.example.restaurant_system.entity.*;
import com.example.restaurant_system.exception.*;
import com.example.restaurant_system.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public interface OrderService {
    OrderResponse placeOrder(PlaceOrderRequest request);
    OrderResponse cancelOrder(Long orderId, Long customerId);
    List<OrderResponse> getOrdersByCustomer(Long customerId);
    List<OrderResponse> getActiveOrders();
    OrderResponse updateOrderStatus(Long orderId, UpdateStatusRequest request);
}

@Service
class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final MenuItemRepository menuRepo;
    private final UserRepository userRepo;

    public OrderServiceImpl(OrderRepository orderRepo, MenuItemRepository menuRepo, UserRepository userRepo) {
        this.orderRepo = orderRepo;
        this.menuRepo = menuRepo;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public OrderResponse placeOrder(PlaceOrderRequest req) {
        User customer = userRepo.findById(req.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest itReq : req.getItems()) {
            MenuItem menu = menuRepo.findById(itReq.getItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found: " + itReq.getItemId()));

            if (!menu.isAvailable()) {
                throw new InvalidOrderOperationException("Menu item not available: " + menu.getName());
            }

            BigDecimal itemTotal = BigDecimal.valueOf(menu.getPrice()) // if price is double
            .multiply(BigDecimal.valueOf(itReq.getQuantity()));

            total = total.add(itemTotal);

            OrderItem oi = new OrderItem();
            oi.setMenuItem(menu);
            oi.setQuantity(itReq.getQuantity());
            oi.setPrice(itemTotal);

            orderItems.add(oi);
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(total);

        orderItems.forEach(oi -> oi.setOrder(order));
        order.setOrderItems(orderItems);
        Order savedOrder = orderRepo.save(order);

        return mapToResponse(savedOrder);
    }

    @Override
    @Transactional
    public OrderResponse cancelOrder(Long orderId, Long customerId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!order.getCustomer().getUserId().equals(customerId)) {
            throw new InvalidOrderOperationException("You can cancel only your own orders");
        }

        if (order.getStatus() == OrderStatus.PENDING || order.getStatus() == OrderStatus.ACCEPTED) {
            order.setStatus(OrderStatus.CANCELLED);
            orderRepo.save(order);
            return mapToResponse(order);
        } else {
            throw new InvalidOrderOperationException("Cannot cancel order in status: " + order.getStatus());
        }
    }

    @Override
    public List<OrderResponse> getOrdersByCustomer(Long customerId) {
        return orderRepo.findByCustomerUserIdOrderByOrderDateDesc(customerId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getActiveOrders() {
        List<OrderStatus> active = Arrays.asList(
                OrderStatus.PENDING, OrderStatus.ACCEPTED, OrderStatus.PREPARING, OrderStatus.READY
        );
        return orderRepo.findByStatusIn(active)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, UpdateStatusRequest request) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        OrderStatus newStatus;
        try {
            newStatus = OrderStatus.valueOf(request.getStatus());
        } catch (IllegalArgumentException e) {
            throw new InvalidOrderOperationException("Invalid status");
        }

        if (!isValidTransition(order.getStatus(), newStatus)) {
            throw new InvalidOrderOperationException("Invalid status transition: " + order.getStatus() + " -> " + newStatus);
        }

        order.setStatus(newStatus);
        orderRepo.save(order);

        return mapToResponse(order);
    }

    private boolean isValidTransition(OrderStatus current, OrderStatus next) {
        if (current == OrderStatus.PENDING && (next == OrderStatus.ACCEPTED || next == OrderStatus.CANCELLED)) return true;
        if (current == OrderStatus.ACCEPTED && (next == OrderStatus.PREPARING || next == OrderStatus.CANCELLED)) return true;
        if (current == OrderStatus.PREPARING && next == OrderStatus.READY) return true;
        if (current == OrderStatus.READY && next == OrderStatus.DELIVERED) return true;
        return false;
    }

    private OrderResponse mapToResponse(Order order) {
        OrderResponse resp = new OrderResponse();
        resp.setOrderId(order.getOrderId());
        resp.setStatus(order.getStatus().name());
        resp.setOrderDate(order.getOrderDate());
        resp.setTotalAmount(order.getTotalAmount());

        List<OrderItemResponse> items = order.getOrderItems().stream().map(oi -> {
            OrderItemResponse ir = new OrderItemResponse();
            ir.setItemId(oi.getMenuItem().getId());
            ir.setName(oi.getMenuItem().getName());
            ir.setQuantity(oi.getQuantity());
            ir.setPrice(oi.getPrice());
            return ir;
        }).collect(Collectors.toList());

        resp.setItems(items);
        return resp;
    }
}
