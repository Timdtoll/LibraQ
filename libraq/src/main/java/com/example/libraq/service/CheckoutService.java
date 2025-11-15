package com.example.libraq.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.libraq.model.Book;
import com.example.libraq.model.CheckoutReceipt;
import com.example.libraq.model.Users;
import com.example.libraq.repository.BookRepository;
import com.example.libraq.repository.CheckoutReceiptRepository;

import jakarta.transaction.Transactional;

@Service
public class CheckoutService {

    private final CheckoutReceiptRepository checkoutRepo;
    private final BookRepository bookRepo;

    public CheckoutService(CheckoutReceiptRepository checkoutRepo, BookRepository bookRepo) {
        this.checkoutRepo = checkoutRepo;
        this.bookRepo = bookRepo;
    }

    @Transactional
    public void checkoutBook(Users user, Book book) {
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is not available.");
        }

        CheckoutReceipt checkout = new CheckoutReceipt(user, book);
        book.setAvailable(false);

        bookRepo.save(book);
        checkoutRepo.save(checkout);
    }

    // Will be useful when librarioan checks in books
    @Transactional
    public void returnBook(CheckoutReceipt checkout) {
        checkout.setReturnDate(LocalDate.now());
        checkout.getBook().setAvailable(true);
        bookRepo.save(checkout.getBook());
        checkoutRepo.save(checkout);
    }

    // Will be needed to display user's checkout history
    public List<CheckoutReceipt> getUserCheckouts(Users user) {
        return checkoutRepo.findByUser(user);
    }

    public CheckoutReceipt getActiveCheckoutByBook(Book book) {
        return checkoutRepo.findByBookAndReturnDateIsNull(book)
                .orElseThrow(() -> new IllegalArgumentException("No active checkout found for the given book."));
    }
}
