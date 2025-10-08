package com.example.restaurant_system.dto;

public class MenuItemDTO {
    private Long itemId;
    private String name;
    private String category;
    private Double price;
    private boolean available;

    public MenuItemDTO(Long itemId, String name, String category, Double price, boolean available) {
        this.itemId = itemId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.available = available;
    }

    // Getters and Setters
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
