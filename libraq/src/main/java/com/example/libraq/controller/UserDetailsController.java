package com.example.libraq.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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


}