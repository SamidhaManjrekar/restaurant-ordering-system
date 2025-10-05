package com.example.restaurant_system.service;

import com.example.restaurant_system.dto.ReviewDTO;
import com.example.restaurant_system.entity.MenuItem;
import com.example.restaurant_system.entity.Review;
import com.example.restaurant_system.entity.User;
import com.example.restaurant_system.exception.NotFoundException;
import com.example.restaurant_system.repository.MenuItemRepository;
import com.example.restaurant_system.repository.ReviewRepository;
import com.example.restaurant_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private UserRepository userRepository;

    // Add a new review
    public ReviewDTO addReview(Long customerId, Long itemId, Review review) {
        User user = userRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        MenuItem menuItem = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Menu item not found"));

        review.setCustomer(user);
        review.setMenuItem(menuItem);
        review.setCreatedAt(LocalDateTime.now());

        Review savedReview = reviewRepository.save(review);

        return convertToDTO(savedReview);
    }

    // Update a review (returns DTO)
    public ReviewDTO updateReview(Long reviewId, Long customerId, Review reviewDetails) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("Review not found"));

        if (!review.getCustomer().getUserId().equals(customerId)) {
            throw new RuntimeException("You can only edit your own review");
        }

        review.setRating(reviewDetails.getRating());
        review.setComment(reviewDetails.getComment());
        review.setCreatedAt(LocalDateTime.now()); // optional: refresh updated time

        Review updatedReview = reviewRepository.save(review);
        return convertToDTO(updatedReview);
    }

    // Delete a review
    public void deleteReview(Long reviewId, Long customerId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("Review not found"));

        if (!review.getCustomer().getUserId().equals(customerId)) {
            throw new RuntimeException("You can only delete your own review");
        }

        reviewRepository.delete(review);
    }

    // Get all reviews for a specific menu item
    public List<ReviewDTO> getReviewsForMenuItem(Long itemId) {
        MenuItem menuItem = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Menu item not found"));

        return reviewRepository.findByMenuItem(menuItem)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get all reviews (optional helper)
    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // --- Helper method to convert Entity → DTO ---
    private ReviewDTO convertToDTO(Review review) {
        return new ReviewDTO(
                review.getReviewId(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt(),
                review.getCustomer().getName(),
                review.getMenuItem().getName()
        );
    }
}
