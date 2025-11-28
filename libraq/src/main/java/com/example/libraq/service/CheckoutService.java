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
    private final int MAX_EXTENSIONS = 3;
    private final ReservationService reservationService;
    static public final int MAX_EXTENSIONS_ERROR_CODE = 409;
    static public final int BOOK_RESERVED_CODE = 410;

    public CheckoutService(CheckoutReceiptRepository checkoutRepo, BookRepository bookRepo, ReservationService reservationService) {
        this.checkoutRepo = checkoutRepo;
        this.bookRepo = bookRepo;
        this.reservationService = reservationService;
    }

    public CheckoutReceipt getCheckoutById(Long id) {
        return checkoutRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No checkout found for the given ID."));
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

    public int extendCheckout(CheckoutReceipt checkout) {
        if (checkout.getReturnDate() != null) {
            throw new IllegalStateException("Cannot extend a returned book.");
        }

        if (reservationService.hasAnyReservations(checkout.getBook())) {
            return BOOK_RESERVED_CODE; //someone has reserved the book
        }

        if(checkout.getExtensionCount() >= MAX_EXTENSIONS) {
            return MAX_EXTENSIONS_ERROR_CODE; //max extensions reached
        }   

        checkout.setDueDate(checkout.getDueDate().plusDays(14));
        checkout.setExtensionCount(checkout.getExtensionCount() + 1);
        checkoutRepo.save(checkout);
        return 0;
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
