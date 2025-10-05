package com.example.restaurant_system.repository;

import com.example.restaurant_system.entity.Review;
import com.example.restaurant_system.entity.MenuItem;
import com.example.restaurant_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMenuItem(MenuItem menuItem);
    List<Review> findByCustomer(User customer);
}
