package com.example.restaurant_system.repository;


import com.example.restaurant_system.entity.Order;
import com.example.restaurant_system.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {
List<Order> findByCustomerUserIdOrderByOrderDateDesc(Long customerId);
List<Order> findByStatusIn(List<OrderStatus> statuses);
}