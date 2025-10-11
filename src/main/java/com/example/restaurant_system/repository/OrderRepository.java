package com.example.restaurant_system.repository;

import com.example.restaurant_system.entity.Order;
import com.example.restaurant_system.entity.OrderStatus;
import com.example.restaurant_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerOrderByOrderDateDesc(User customer);
    List<Order> findByStatusInOrderByOrderDateDesc(List<OrderStatus> statuses);
}