package com.example.restaurant_system.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private MenuItem menuItem; // assume MenuItem entity exists

    private Integer quantity;

    private BigDecimal price; // price at time of order (menuItem.price * qty)

    // ---- Constructors ----
    public OrderItem() {
    }

    public OrderItem(Long orderItemId, Order order, MenuItem menuItem, Integer quantity, BigDecimal price) {
        this.orderItemId = orderItemId;
        this.order = order;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.price = price;
    }

    // ---- Getters and Setters ----
    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    // ---- toString ----
    @Override
    public String toString() {
        return "OrderItem{" +
                "orderItemId=" + orderItemId +
                ", menuItem=" + (menuItem != null ? menuItem.getName() : null) +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
