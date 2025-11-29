package com.example.libraq.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.libraq.model.Users;
import com.example.libraq.service.BookService;
import com.example.libraq.service.UserService;

@Controller
public class LibrarianConsoleController {

    private final BookService bookService;
    private final UserService userService;
    
    public LibrarianConsoleController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping("/librarianpage")
    public String librarianPage(Model model) {
        //Add Librarian to model
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		
		Users user = userService.findByEmail(currentPrincipalName).get(0);
		model.addAttribute("user", user);

        //Add all books to model
        model.addAttribute("books", bookService.getAllBooks());

        return "librarianpage"; // Thymeleaf template name for librarian console
    }
}