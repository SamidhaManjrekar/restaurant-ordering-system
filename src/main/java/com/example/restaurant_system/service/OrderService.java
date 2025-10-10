package com.example.restaurant_system.service;

import com.example.restaurant_system.dto.*;
import com.example.restaurant_system.dto.OrderDTO.OrderItemRequest;
import com.example.restaurant_system.dto.OrderDTO.PlaceOrderRequest;
import com.example.restaurant_system.entity.*;
import com.example.restaurant_system.exception.NotFoundException;
import com.example.restaurant_system.repository.MenuItemRepository;
import com.example.restaurant_system.repository.OrderRepository;
import com.example.restaurant_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    // Place order (Customer)
    @Transactional
   public OrderDTO placeOrder(PlaceOrderRequest request) {
    User customer = userRepository.findById(request.getCustomerId())
            .orElseThrow(() -> new NotFoundException("Customer not found"));

    Order order = new Order();
    order.setCustomer(customer);
    order.setDeliveryAddress(request.getDeliveryAddress());
    order.setSpecialInstructions(request.getSpecialInstructions());

    Double totalAmount = 0.0;

    // Process each order item
    for (OrderItemRequest itemRequest : request.getItems()) {
        MenuItem menuItem = menuItemRepository.findById(itemRequest.getMenuItemId())
                .orElseThrow(() -> new NotFoundException("Menu item not found with id: " + itemRequest.getMenuItemId()));

        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItem(menuItem);
        orderItem.setQuantity(itemRequest.getQuantity());
        orderItem.setPrice(menuItem.getPrice()); // Store current price

        totalAmount += menuItem.getPrice() * itemRequest.getQuantity();
        order.addOrderItem(orderItem);
    }

    order.setTotalAmount(totalAmount);
    Order savedOrder = orderRepository.save(order);

    return convertToDTO(savedOrder);
}

    // Cancel order (Customer)
    @Transactional
    public OrderDTO cancelOrder(Long orderId, Long customerId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        if (!order.getCustomer().getUserId().equals(customerId)) {
            throw new RuntimeException("You can only cancel your own orders");
        }

        // Only allow cancellation for pending orders
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Order cannot be cancelled in its current status");
        }

        order.setStatus(OrderStatus.CANCELLED);
        Order updatedOrder = orderRepository.save(order);

        return convertToDTO(updatedOrder);
    }

    // View order history (Customer)
    public List<OrderDTO> getOrderHistory(Long customerId) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        return orderRepository.findByCustomerOrderByOrderDateDesc(customer)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // View all active orders (Staff)
    public List<OrderDTO> getAllActiveOrders() {
        List<OrderStatus> activeStatuses = List.of(
                OrderStatus.PENDING,
                OrderStatus.CONFIRMED,
                OrderStatus.PREPARING,
                OrderStatus.OUT_FOR_DELIVERY
        );

        return orderRepository.findByStatusInOrderByOrderDateDesc(activeStatuses)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Update order status (Staff)
    @Transactional
    public OrderDTO updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);

        return convertToDTO(updatedOrder);
    }

    // Helper method to convert Entity → DTO
    private OrderDTO convertToDTO(Order order) {
        List<OrderDTO.OrderItemDTO> itemDTOs = order.getOrderItems().stream()
                .map(item -> new OrderDTO.OrderItemDTO(
                        item.getMenuItem().getId(),
                        item.getMenuItem().getName(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .collect(Collectors.toList());

        return new OrderDTO(
                order.getOrderId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus().name(),
                order.getDeliveryAddress(),
                order.getSpecialInstructions(),
                order.getCustomer().getName(),
                itemDTOs
        );
    }
}