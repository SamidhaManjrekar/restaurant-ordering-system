package com.example.restaurant_system.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {

    // ---------------- PlaceOrderRequest ----------------
    public static class PlaceOrderRequest {
        private Long customerId;
        private List<OrderItemRequest> items;

        public Long getCustomerId() { return customerId; }
        public void setCustomerId(Long customerId) { this.customerId = customerId; }

        public List<OrderItemRequest> getItems() { return items; }
        public void setItems(List<OrderItemRequest> items) { this.items = items; }
    }

    // ---------------- OrderItemRequest ----------------
    public static class OrderItemRequest {
        private Long itemId;
        private int quantity;

        public Long getItemId() { return itemId; }
        public void setItemId(Long itemId) { this.itemId = itemId; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }

    // ---------------- UpdateStatusRequest ----------------
    public static class UpdateStatusRequest {
        private String status;

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    // ---------------- OrderResponse ----------------
    public static class OrderResponse {
        private Long orderId;
        private String status;
        private LocalDateTime orderDate;
        private BigDecimal totalAmount;
        private List<OrderItemResponse> items;

        public Long getOrderId() { return orderId; }
        public void setOrderId(Long orderId) { this.orderId = orderId; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public LocalDateTime getOrderDate() { return orderDate; }
        public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

        public BigDecimal getTotalAmount() { return totalAmount; }
        public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

        public List<OrderItemResponse> getItems() { return items; }
        public void setItems(List<OrderItemResponse> items) { this.items = items; }
    }

    // ---------------- OrderItemResponse ----------------
    public static class OrderItemResponse {
        private Long itemId;
        private String name;
        private int quantity;
        private BigDecimal price;

        public Long getItemId() { return itemId; }
        public void setItemId(Long itemId) { this.itemId = itemId; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
    }
}
