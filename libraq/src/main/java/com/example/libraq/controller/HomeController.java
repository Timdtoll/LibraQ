package com.example.libraq.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.libraq.model.Book;
import com.example.libraq.model.Genre;
import com.example.libraq.service.BookService;

@Controller
public class HomeController {

    private final BookService bookService;

    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/home")
    public String home(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "genres", required = false) List<Genre> selectedGenres,
            Model model) {

        List<Book> books;
        // Determine if search filters are applied
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

        return "index"; // Thymeleaf template name
    }
}
