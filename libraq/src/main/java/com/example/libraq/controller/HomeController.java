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
    // bookService.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald",
    // Genre.CLASSICAL, 17183789034L));
    // bookService.addBook(new Book("Cards on the Table", "Agatha Christie",
    // Genre.MYSTERY, 345678901234L));
    // bookService.addBook(new Book("The Hobbit", "J.R.R. Tolkien", Genre.FANTASY,
    // 456789012345L));
    // bookService.addBook(new Book("Dune", "Frank Herbert", Genre.SCIFI,
    // 567890123456L));
    // bookService.addBook(new Book("Pride and Prejudice", "Jane Austen",
    // Genre.ROMANCE, 678901234567L));
    // }

    @GetMapping("/home")
    public String home(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "genres", required = false) List<Genre> selectedGenres,
            Model model) {

        List<Book> books;

        if ((query != null && !query.trim().isEmpty()) || (selectedGenres != null && !selectedGenres.isEmpty())) {
            books = bookService.searchBooks(query, selectedGenres);
        } else {
            books = bookService.getAllBooks();
        }

        // Pass all genres and currently selected ones to the view
        model.addAttribute("genres", Genre.values());
        model.addAttribute("selectedGenres", selectedGenres);
        model.addAttribute("books", books);
        model.addAttribute("query", query);

        return "index";
    }

}
