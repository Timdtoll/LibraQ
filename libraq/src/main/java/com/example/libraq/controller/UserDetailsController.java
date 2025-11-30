package com.example.libraq.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.libraq.model.CheckoutReceipt;
import com.example.libraq.model.Users;
import com.example.libraq.service.CheckoutService;
import com.example.libraq.service.UserService;


@Controller
public class UserDetailsController {

    private final UserService userService;
	private final CheckoutService checkoutService;

	public UserDetailsController(UserService userService, CheckoutService checkoutService) {
        this.userService = userService;
		this.checkoutService = checkoutService;
    }

	@GetMapping("/userdetails")
	public String getUserDetails(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		
		Users user = userService.findByEmail(currentPrincipalName).get(0);
		model.addAttribute("user", user);
		List<CheckoutReceipt> checkouts = checkoutService.getUserCheckouts(user);
		model.addAttribute("checkouts", checkouts);

		return "userdetails"; // templates/userdetails.html
	}

	
	@PostMapping("/extend/{id}")
	@PreAuthorize("hasRole('RENTER')")
	public String extendCheckout(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {

		CheckoutReceipt checkout = checkoutService.getCheckoutById(id);
		Users user = userService.findByEmail(principal.getName()).get(0);

		// Ensure the book belongs to this user
		if (!checkout.getUser().getId().equals(user.getId())) {
			redirectAttributes.addFlashAttribute("extendError",
					"You are not authorized to extend this checkout.");
			return "redirect:/userdetails";
		}

		try {
			int result = checkoutService.extendCheckout(checkout);

			switch (result) {
				case CheckoutService.BOOK_RESERVED_CODE:
					redirectAttributes.addFlashAttribute("extendError",
							"Due date could not be extended as this book is reserved by someone else.");
					break;

				case CheckoutService.MAX_EXTENSIONS_ERROR_CODE:
					redirectAttributes.addFlashAttribute("extendError",
							"This book could not be extended as you have reached the maximum number of extensions.");
					break;

				default:
					redirectAttributes.addFlashAttribute("extendSuccess",
							"The due date is successfully extended by two weeks.");
			}

		} catch (IllegalStateException e) {
			redirectAttributes.addFlashAttribute("extendError", e.getMessage());
		}

		return "redirect:/userdetails";
	}

}