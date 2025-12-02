package com.example.libraq.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "RESERVATIONS")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The user who made the reservation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    // The book being reserved
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_isbn")
    private Book book;

    private LocalDateTime createdDate;         // placed reservation
    private LocalDateTime holdExpirationDate;  // only set when book is returned

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    protected Reservation() {}

    public Reservation(Users user, Book book) {
        this.user = user;
        this.book = book;
        this.createdDate = LocalDateTime.now();
        this.status = ReservationStatus.ACTIVE;
    }

    // Getters and setters

    public Long getId() { return id; }
    public Users getUser() { return user; }
    public Book getBook() { return book; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public LocalDateTime getHoldExpirationDate() { return holdExpirationDate; }
    public ReservationStatus getStatus() { return status; }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public void setHoldExpirationDate(LocalDateTime holdExpirationDate) {
        this.holdExpirationDate = holdExpirationDate;
    }
}
