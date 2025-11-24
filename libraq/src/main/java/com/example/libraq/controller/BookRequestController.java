package com.example.libraq.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.libraq.dto.BookRequestDto;
import com.example.libraq.model.BookRequest;
import com.example.libraq.model.Users;
import com.example.libraq.service.BookRequestService;
import com.example.libraq.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/book-requests")
public class BookRequestController {
    private final BookRequestService bookRequestService;
    private final UserService userService;
    

    public BookRequestController(BookRequestService bookRequestService, UserService userService) {
        this.bookRequestService = bookRequestService;
        this.userService = userService;
    }

    @GetMapping("/manage")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public String getAllRequests(Model model) {
        model.addAttribute("requests", bookRequestService.getAllRequests());
        return "book-requests.html";
    }

    @GetMapping("/")
    public String showForm(Model model) {
        if (!model.containsAttribute("dto")) {
            model.addAttribute("dto", new BookRequestDto());
        }
        return "book-request-form.html";
    }
    
    @GetMapping("/my-requests")
    public String myRequests(Principal principal, Model model) {
        Users user = userService.findByEmail(principal.getName()).get(0);
        model.addAttribute("requests", bookRequestService.getRequestsByRequestor(user));
        return "my-requests.html";
    }

    @PostMapping
    public String createRequest(@Valid BookRequestDto dto, BindingResult result, Model model, 
                               Principal principal, RedirectAttributes redirectAttributes) {
       if(result.hasErrors()) {
        model.addAttribute("dto", dto);
        return "book-request-form.html";
       }

       Users requestor = userService.findByEmail(principal.getName()).get(0);
       BookRequest bookRequest = new BookRequest(dto.getTitle(), dto.getAuthor(), requestor, dto.getNotes());
       bookRequestService.createRequest(bookRequest);

       redirectAttributes.addFlashAttribute("success", "Book request created successfully!");
       return "redirect:/book-requests/";
    }

   
    @PostMapping("/{id}/approve")
    public String approveRequest(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        BookRequest bookRequest = bookRequestService.getById(id);
        bookRequestService.approveRequest(bookRequest);
        redirectAttributes.addFlashAttribute("success", "Book request approved successfully");
        return "redirect:/book-requests/manage";
    }

}
