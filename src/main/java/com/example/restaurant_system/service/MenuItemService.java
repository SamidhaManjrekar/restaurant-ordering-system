package com.example.restaurant_system.service;

import com.example.restaurant_system.entity.MenuItem;
import com.example.restaurant_system.exception.NotFoundException;
import com.example.restaurant_system.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    // Create new menu item
    public MenuItem addMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    // Update menu item
    public MenuItem updateMenuItem(Long id, MenuItem menuItemDetails) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MenuItem not found with id " + id));

        menuItem.setName(menuItemDetails.getName());
        menuItem.setDescription(menuItemDetails.getDescription());
        menuItem.setCategory(menuItemDetails.getCategory());
        menuItem.setPrice(menuItemDetails.getPrice());
        menuItem.setAvailable(menuItemDetails.isAvailable());

        return menuItemRepository.save(menuItem);
    }

    // Delete menu item
    public void deleteMenuItem(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MenuItem not found with id " + id));
        menuItemRepository.delete(menuItem);
    }

    // Get all menu items
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    // Get single menu item
    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MenuItem not found with id " + id));
    }
}