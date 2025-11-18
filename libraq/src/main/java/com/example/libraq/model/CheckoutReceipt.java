package com.example.libraq.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CheckoutReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private LocalDate returnDate; // null if not returned yet
    private Double fineAmount; // null if no fine

    public CheckoutReceipt() {
    }

    public CheckoutReceipt(Users user, Book book) {
        this.user = user;
        this.book = book;
        this.checkoutDate = LocalDate.now();
        this.dueDate = checkoutDate.plusWeeks(2);
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Users getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public Double getFineAmount() {
        return fineAmount;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

}
