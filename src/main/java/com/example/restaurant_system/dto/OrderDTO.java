package com.example.restaurant_system.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Long orderId;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private String status;
    private String deliveryAddress;
    private String specialInstructions;
    private String customerName;
    private List<OrderItemDTO> orderItems;

    // Constructors
    public OrderDTO() {}

    public OrderDTO(Long orderId, LocalDateTime orderDate, Double totalAmount, 
                   String status, String deliveryAddress, String specialInstructions, 
                   String customerName, List<OrderItemDTO> orderItems) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.deliveryAddress = deliveryAddress;
        this.specialInstructions = specialInstructions;
        this.customerName = customerName;
        this.orderItems = orderItems;
    }

    // Getters and Setters
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public String getSpecialInstructions() { return specialInstructions; }
    public void setSpecialInstructions(String specialInstructions) { this.specialInstructions = specialInstructions; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public List<OrderItemDTO> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItemDTO> orderItems) { this.orderItems = orderItems; }

    // OrderItemDTO inner class
    public static class OrderItemDTO {
        private Long menuItemId;
        private String menuItemName;
        private Integer quantity;
        private Double price;

        // Constructors
        public OrderItemDTO() {}

        public OrderItemDTO(Long menuItemId, String menuItemName, Integer quantity, Double price) {
            this.menuItemId = menuItemId;
            this.menuItemName = menuItemName;
            this.quantity = quantity;
            this.price = price;
        }

        // Getters and Setters
        public Long getMenuItemId() { return menuItemId; }
        public void setMenuItemId(Long menuItemId) { this.menuItemId = menuItemId; }

        public String getMenuItemName() { return menuItemName; }
        public void setMenuItemName(String menuItemName) { this.menuItemName = menuItemName; }

        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }

        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }
    }

    // PlaceOrderRequest inner class
    public static class PlaceOrderRequest {
        private Long customerId;
        private String deliveryAddress;
        private String specialInstructions;
        private List<OrderItemRequest> items;

        // Getters and Setters
        public Long getCustomerId() { return customerId; }
        public void setCustomerId(Long customerId) { this.customerId = customerId; }

        public String getDeliveryAddress() { return deliveryAddress; }
        public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

        public String getSpecialInstructions() { return specialInstructions; }
        public void setSpecialInstructions(String specialInstructions) { this.specialInstructions = specialInstructions; }

        public List<OrderItemRequest> getItems() { return items; }
        public void setItems(List<OrderItemRequest> items) { this.items = items; }
    }

    // OrderItemRequest inner class
    public static class OrderItemRequest {
        private Long menuItemId;
        private Integer quantity;

        // Getters and Setters
        public Long getMenuItemId() { return menuItemId; }
        public void setMenuItemId(Long menuItemId) { this.menuItemId = menuItemId; }

        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }

    // StatusUpdateRequest inner class (for staff to update order status)
    public static class StatusUpdateRequest {
        private OrderStatus status;

        // Getters and Setters
        public OrderStatus getStatus() { return status; }
        public void setStatus(OrderStatus status) { this.status = status; }
    }

    // OrderStatus enum (if you want to keep it in DTO)
    public enum OrderStatus {
        PENDING, CONFIRMED, PREPARING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
    }
}