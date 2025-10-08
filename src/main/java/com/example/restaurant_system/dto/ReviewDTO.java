package com.example.restaurant_system.dto;

import java.time.LocalDateTime;

public class ReviewDTO {
    private Long reviewId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;

    private String customerName;
    private String itemName;

    public ReviewDTO(Long reviewId, int rating, String comment, LocalDateTime createdAt, String customerName,
            String itemName) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.customerName = customerName;
        this.itemName = itemName;
    }

    // Getters and Setters
    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
