package com.example.libraq.controller;

import com.example.libraq.model.Book;
import com.example.libraq.model.Genre;
import com.example.libraq.service.BookService;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final BookService bookService;

    @Autowired
    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    // @PostConstruct
    // public void initialize() {
    // // This method is used to add initial books for testing
    // bookService.clear();
    // bookService.addBook(new Book("1984", "George Orwell", Genre.CLASSICAL,
    // 234567890123L));
    // bookService.addBook(new Book("To Kill a Mockingbird", "Harper Lee",
    // Genre.CLASSICAL, 123456789023L));
    // bookService.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald",
    // Genre.CLASSICAL, 17183789034L));
    // }

    @GetMapping("/home")
    public String home(@RequestParam(value = "query", required = false) String query, Model model) {
        List<Book> books;
        if (query != null && !query.trim().isEmpty()) {
            // Combine author and title search
            List<Book> byTitle = bookService.findByTitle(query);
            List<Book> byAuthor = bookService.findByAuthor(query);
            books = byTitle;
            byAuthor.stream()
                    .filter(book -> !books.contains(book))
                    .forEach(books::add);
        } else {
            books = bookService.getAllBooks();
        }

        model.addAttribute("books", books);
        model.addAttribute("query", query);
        return "index";
    }
}
