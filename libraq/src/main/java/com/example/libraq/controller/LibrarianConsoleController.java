package com.example.libraq.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.libraq.dto.AddBookDto;
import com.example.libraq.dto.RemoveBookDto;
import com.example.libraq.model.Book;
import com.example.libraq.model.Users;
import com.example.libraq.service.BookRequestService;
import com.example.libraq.service.BookService;
import com.example.libraq.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LibrarianConsoleController {

    private final BookRequestService bookRequestService;
    private final BookService bookService;
    private final UserService userService;
    
    public LibrarianConsoleController(BookService bookService, UserService userService, BookRequestService bookRequestService) {
        this.bookService = bookService;
        this.userService = userService;
        this.bookRequestService = bookRequestService;
    }

    @GetMapping("/librarianpage")
    public String librarianPage(Model model) {
        //Add dto to model
        if (!model.containsAttribute("addDto")) {
            model.addAttribute("addDto", new AddBookDto());
        }
        if (!model.containsAttribute("removeDto")) {
            model.addAttribute("removeDto", new RemoveBookDto());
        }

        //Add Librarian to model
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		
		Users user = userService.findByEmail(currentPrincipalName).get(0);
		model.addAttribute("user", user);

        //Add all books to model
        model.addAttribute("books", bookService.getAllBooks());

        //Add all book requests to model, seperate pending requests and add to model
        model.addAttribute("requests", bookRequestService.getAllRequests());

        model.addAttribute("pendingRequests", bookRequestService.getRequestsByStatus(com.example.libraq.model.RequestStatus.PENDING));

        return "librarianpage"; // Thymeleaf template name for librarian console
    }

    @PostMapping("/addBook")
    public String addBook(@Valid AddBookDto addDto ,BindingResult result, Model model, HttpSession session) {
        // 1) Make sure other DTO in model
        if (!model.containsAttribute("removeDto")) {
            model.addAttribute("removeDto", new RemoveBookDto());
        }

        // 2) Bean Validation errors (NotBlank, Email, Size, etc.)
        if (result.hasErrors()) {
            model.addAttribute("addDto", addDto);
            return "librarianpage.html";
        }

        // 3) Book ISBN must be unique / not yet exist
        boolean bookExists = bookService.bookExists(addDto.getISBN());
        if (bookExists) {
            result.rejectValue("ISBN", "duplicate", "book ISBN already registered");
            model.addAttribute("addDto", addDto);
            return "librarianpage";
        }

        // 4) Create the book
        Book newBook = new Book(addDto.getTitle(), addDto.getAuthor(), addDto.getGenre(), addDto.getISBN());
        bookService.addBook(newBook);

        // 5) On success, redirect back to console
        return "redirect:/librarianpage";
    }

    @PostMapping("/removeBook")
    public String removeBook(@Valid RemoveBookDto removeDto ,BindingResult result, Model model, HttpSession session) {
        // 1) Make sure other DTO in model
        if (!model.containsAttribute("addDto")) {
            model.addAttribute("addDto", new AddBookDto());
        }

        // 2) Check if book exists
        boolean bookExists = bookService.bookExists(removeDto.getISBN());
        if (!bookExists) {
            result.rejectValue("ISBN", "does not exist", "book ISBN does not exist");
            model.addAttribute("removeDto", removeDto);
            return "librarianpage";
        }

        // 3) Remove the book
        bookService.removeBook(removeDto.getISBN());

        // 4) On success, redirect back to console
        return "redirect:/librarianpage";
    }
}