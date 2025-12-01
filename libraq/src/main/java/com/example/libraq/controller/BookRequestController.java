package com.example.libraq.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.example.libraq.model.RequestStatus;
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
    public String getAllRequests(
            @RequestParam(value = "status", required = false) String statusFilter,
            Model model) {
        
        // Get all requests for statistics
        List<BookRequest> allRequests = bookRequestService.getAllRequests();
        
        // Calculate statistics (always show all counts)
        long pendingCount = allRequests.stream()
            .filter(r -> r.getStatus().name().equals("PENDING"))
            .count();
        long approvedCount = allRequests.stream()
            .filter(r -> r.getStatus().name().equals("APPROVED"))
            .count();
        long rejectedCount = allRequests.stream()
            .filter(r -> r.getStatus().name().equals("REJECTED"))
            .count();
        
        // Filter requests by status if filter is provided
        List<BookRequest> filteredRequests;
        if (statusFilter != null && !statusFilter.isEmpty() && !statusFilter.equals("ALL")) {
            try {
                RequestStatus status = RequestStatus.valueOf(statusFilter.toUpperCase());
                filteredRequests = bookRequestService.getRequestsByStatus(status);
            } catch (IllegalArgumentException e) {
                // Invalid status, show all
                filteredRequests = allRequests;
                statusFilter = null;
            }
        } else {
            // Show all requests
            filteredRequests = allRequests;
            statusFilter = "ALL";
        }
        
        model.addAttribute("requests", filteredRequests);
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("approvedCount", approvedCount);
        model.addAttribute("rejectedCount", rejectedCount);
        model.addAttribute("totalCount", allRequests.size());
        model.addAttribute("activeFilter", statusFilter); // For highlighting active filter
        
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
    @PreAuthorize("hasRole('LIBRARIAN')")
    public String approveRequest(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            BookRequest bookRequest = bookRequestService.getById(id);
            bookRequestService.approveRequest(bookRequest);
            redirectAttributes.addFlashAttribute("success", "Book request approved successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Request not found: " + e.getMessage());
        }
        return "redirect:/book-requests/manage";
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public String rejectRequest(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            BookRequest bookRequest = bookRequestService.getById(id);
            bookRequestService.rejectRequest(bookRequest);
            redirectAttributes.addFlashAttribute("success", "Book request rejected successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Request not found: " + e.getMessage());
        }
        return "redirect:/book-requests/manage";
    }

}
