package com.example.libraq.repository;

import com.example.libraq.model.Reservation;
import com.example.libraq.model.ReservationStatus;
import com.example.libraq.model.Book;
import com.example.libraq.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Get all reservations for a book
    List<Reservation> findByBookOrderByCreatedDateAsc(Book book);

    // Get active reservation queue
    List<Reservation> findByBookAndStatusOrderByCreatedDateAsc(Book book, ReservationStatus status);

    // Find reservation for specific user & book
    List<Reservation> findByBookAndUser(Book book, Users user);

    // Get all reservations ready for pickup (HOLD_READY)
    List<Reservation> findByStatus(ReservationStatus status);
}
