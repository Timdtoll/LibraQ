package com.example.libraq.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.libraq.model.Book;
import com.example.libraq.model.Users;
import com.example.libraq.service.BookService;
import com.example.libraq.service.CheckoutService;
import com.example.libraq.service.ReservationService;
import com.example.libraq.service.UserService;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final CheckoutService checkoutService;
    private final UserService userService;
    private final ReservationService reservationService;

    public BookController(BookService bookService, CheckoutService checkoutService, UserService userService, ReservationService reservationService) {
        this.bookService = bookService;
        this.checkoutService = checkoutService;
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @GetMapping("/{isbn}")
    public String viewBook(@PathVariable Long isbn, Model model) {
        Optional<Book> bookOpt = bookService.findByISBN(isbn);
        if (bookOpt.isEmpty()) {
            return "redirect:/?error=bookNotFound";
        }
        Book book = bookOpt.get();
        model.addAttribute("book", book);
        if (!book.isAvailable()) {
            try {
                model.addAttribute("activeCheckout", checkoutService.getActiveCheckoutByBook(book));
            } catch (IllegalArgumentException e) {
                // No active checkout found, do nothing
            }
        }
        return "book-details.html";
    }

    @PostMapping("/{isbn}/checkout")
    @PreAuthorize("hasRole('RENTER')")
    public String checkoutBook(@PathVariable Long isbn, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            Users user = userService.findByEmail(principal.getName()).get(0);
            Book book = bookService.getBookByISBN(isbn);
            checkoutService.checkoutBook(user, book);

            redirectAttributes.addFlashAttribute("checkoutSuccess",
                    "You have successfully checked out " + book.getTitle() + " by " + book.getAuthor() + ".");

        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("checkoutError", e.getMessage());
        }

        return "redirect:/books/" + isbn;
    }
    @PostMapping("/{isbn}/reserve")
    @PreAuthorize("hasRole('RENTER')")
    public String reserveBook(@PathVariable Long isbn, Principal principal, RedirectAttributes redirectAttributes) {
        Users user = userService.findByEmail(principal.getName()).get(0);
        Book book = bookService.getBookByISBN(isbn);
        

         // Prevent duplicate reservations
        if (reservationService.userHasReservation(book, user)) {
            redirectAttributes.addFlashAttribute("reservationError",
                    "You already have a reservation for this book.");
            return "redirect:/books/" + isbn;
        }
         // Create reservation
        reservationService.reserveBook(book, user);
        redirectAttributes.addFlashAttribute("reservationSuccess",
        "You have reserved " + book.getTitle() + ". You will be notified when it becomes available.");

        return "redirect:/books/" + isbn;
    }
    
}
