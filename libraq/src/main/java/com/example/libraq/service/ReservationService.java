package com.example.libraq.service;

import com.example.libraq.model.*;
import com.example.libraq.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepo;

    public ReservationService(ReservationRepository reservationRepo) {
        this.reservationRepo = reservationRepo;
    }

    // Create new reservation
    public Reservation reserveBook(Book book, Users user) {
        Reservation reservation = new Reservation(user, book);
        return reservationRepo.save(reservation);
    }

    // Get next ACTIVE reservation in queue
    public Reservation getNextActiveReservation(Book book) {
        List<Reservation> queue =
                reservationRepo.findByBookAndStatusOrderByCreatedDateAsc(book, ReservationStatus.ACTIVE);

        return queue.isEmpty() ? null : queue.get(0);
    }

    // Get first hold-ready reservation (book is available for pickup)
    public Reservation getHoldReadyReservation(Book book) {
        List<Reservation> list =
                reservationRepo.findByBookAndStatusOrderByCreatedDateAsc(book, ReservationStatus.HOLD_READY);

        return list.isEmpty() ? null : list.get(0);
    }

    // Check if any reservations exist for book
    public boolean hasAnyReservations(Book book) {
        return !reservationRepo.findByBookOrderByCreatedDateAsc(book).isEmpty();
    }

    // Check if book is reserved by any user except the logged-in user
    public boolean isReservedByAnotherUser(Book book, Users user) {
        Reservation next = getNextActiveReservation(book);

        return next != null && !next.getUser().getId().equals(user.getId());
    }

    // Check if user has reserved this book
    public boolean userHasReservation(Book book, Users user) {
        return !reservationRepo.findByBookAndUser(book, user).isEmpty();
    }

    // Change first reservation to HOLD_READY when book is returned
    @Transactional
    public Reservation activateNextReservation(Book book) {
        Reservation next = getNextActiveReservation(book);

        if (next == null) return null;

        next.setStatus(ReservationStatus.HOLD_READY);
        next.setHoldExpirationDate(LocalDateTime.now().plusDays(3));
        return reservationRepo.save(next);
    }

    // Mark reservation fulfilled when user checks out book during hold
    @Transactional
    public void fulfillReservation(Reservation res) {
        res.setStatus(ReservationStatus.FULFILLED);
        reservationRepo.save(res);
    }
}
