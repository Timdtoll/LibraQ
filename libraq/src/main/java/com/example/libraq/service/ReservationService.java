package com.example.libraq.service;

import com.example.libraq.model.*;
import com.example.libraq.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Scheduled;

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

    // Check if user has reserved this book
    public boolean userHasReservation(Book book, Users user) {
        return !reservationRepo.findByBookAndUser(book, user).isEmpty();
    }

    // Check if user has a HOLD_READY reservation for this book
    public boolean userHasReadyReservation(Book book, Users user) {
        List<Reservation> list = reservationRepo.findByBookAndUserAndStatus(
                book, user, ReservationStatus.HOLD_READY);

        return !list.isEmpty();
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

    @Transactional
    public Reservation processQueueAfterExpiration(Book book) {
        // Check for any ACTIVE reservations
        Reservation next = getNextActiveReservation(book);
        if (next == null) return null;

        // Promote to HOLD_READY
        next.setStatus(ReservationStatus.HOLD_READY);
        next.setHoldExpirationDate(LocalDateTime.now().plusDays(3));
        reservationRepo.save(next);

        return next;
    }

    @Transactional
    public void handleBookReturn(Book book) {

        // Try to process holds
        Reservation activated = processQueueAfterExpiration(book);

        if (activated != null) {
            // book is on hold for next user, so not available
            book.setAvailable(false);
            return;
        }

        // if no reservations then book becomes fully available
        book.setAvailable(true);
    }


    @Transactional
    @Scheduled(fixedRate =  60 * 60 * 1000) // runs every hour
    public void expireOldHolds() {

        // Get all reservations that are HOLD_READY (waiting for pickup)
        List<Reservation> holds = 
            reservationRepo.findByStatus(ReservationStatus.HOLD_READY);

        for (Reservation res : holds) {

            // If hold has expired
            if (res.getHoldExpirationDate().isBefore(LocalDateTime.now())) {

                res.setStatus(ReservationStatus.EXPIRED);
                reservationRepo.save(res);

                // After expiring, activate the next person in line (if any)
                activateNextReservation(res.getBook());
            }
        }
    }
}
