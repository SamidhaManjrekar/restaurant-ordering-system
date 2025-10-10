package com.example.restaurant_system.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public class TableReservationDTO {

    @NotNull(message = "Customer name is required")
    private String customerName;

    @NotNull(message = "Phone number is required")
    private String phoneNumber;

    @Positive(message = "Table number must be positive")
    private int tableNumber;

    @Positive(message = "Number of guests must be positive")
    private int numberOfGuests;

    @FutureOrPresent(message = "Reservation time cannot be in the past")
    private LocalDateTime reservationTime;

    // Getters and Setters
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }

    public int getNumberOfGuests() { return numberOfGuests; }
    public void setNumberOfGuests(int numberOfGuests) { this.numberOfGuests = numberOfGuests; }

    public LocalDateTime getReservationTime() { return reservationTime; }
    public void setReservationTime(LocalDateTime reservationTime) { this.reservationTime = reservationTime; }
}
