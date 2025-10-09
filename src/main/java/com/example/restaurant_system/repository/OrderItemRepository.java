package com.example.restaurant_system.repository;


import com.example.restaurant_system.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> { }