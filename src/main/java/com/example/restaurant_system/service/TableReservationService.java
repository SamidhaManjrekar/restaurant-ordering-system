package com.example.restaurant_system.service;

import com.example.restaurant_system.entity.TableReservation;
import java.util.List;

public interface TableReservationService {
    TableReservation createReservation(TableReservation reservation);
    TableReservation getReservationById(Long id);
    List<TableReservation> getAllReservations();
    void deleteReservation(Long id);
}
