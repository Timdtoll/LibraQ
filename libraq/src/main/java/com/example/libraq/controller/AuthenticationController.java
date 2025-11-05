package com.example.libraq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling authentication-related views.
 * 
 * Spring Security handles the actual login POST processing automatically
 * This controller only serves the login page view
 */
@Controller
public class AuthenticationController {
	
	//Displays the login form page.
	@GetMapping("/login")
	public String login() {
		return "login"; // templates/login.html
	}
		
	// @PostMapping
	// public String home() {
	// 	return "/home";
	// }

}
