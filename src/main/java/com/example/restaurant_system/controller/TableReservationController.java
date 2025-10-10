package com.example.restaurant_system.controller;

import com.example.restaurant_system.entity.TableReservation;
import com.example.restaurant_system.service.TableReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class TableReservationController {

    private final TableReservationService reservationService;

    public TableReservationController(TableReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public TableReservation createReservation(@RequestBody TableReservation reservation) {
        return reservationService.createReservation(reservation);
    }

    @GetMapping("/{id}")
    public TableReservation getReservation(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    @GetMapping
    public List<TableReservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }
}
