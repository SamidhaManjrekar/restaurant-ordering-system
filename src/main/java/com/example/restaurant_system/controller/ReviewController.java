package com.example.restaurant_system.controller;

import com.example.restaurant_system.dto.ReviewDTO;
import com.example.restaurant_system.entity.Review;
import com.example.restaurant_system.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // Add a new review
    @PostMapping("/add")
    public ResponseEntity<ReviewDTO> addReview(
            @RequestParam Long customerId,
            @RequestParam Long itemId,
            @RequestBody Review review) {

        ReviewDTO savedReview = reviewService.addReview(customerId, itemId, review);
        return ResponseEntity.ok(savedReview);
    }

    // Update an existing review
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDTO> updateReview(
            @PathVariable Long reviewId,
            @RequestParam Long customerId,
            @RequestBody Review reviewDetails) {

        ReviewDTO updatedReview = reviewService.updateReview(reviewId, customerId, reviewDetails);
        return ResponseEntity.ok(updatedReview);
    }

    // Delete a review
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId,
            @RequestParam Long customerId) {

        reviewService.deleteReview(reviewId, customerId);
        return ResponseEntity.noContent().build();
    }

    // Get reviews for a menu item
    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsForMenuItem(@PathVariable Long itemId) {
        List<ReviewDTO> reviews = reviewService.getReviewsForMenuItem(itemId);
        return ResponseEntity.ok(reviews);
    }

    // (Optional) Get all reviews
    @GetMapping("/all")
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }
}
