package com.example.restaurant_system.service;

import com.example.restaurant_system.entity.TableReservation;
import com.example.restaurant_system.exception.ReservationNotFoundException;
import com.example.restaurant_system.repository.TableReservationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TableReservationServiceImpl implements TableReservationService {

    private final TableReservationRepository reservationRepository;

    public TableReservationServiceImpl(TableReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public TableReservation createReservation(TableReservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public TableReservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + id));
    }

    @Override
    public List<TableReservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
