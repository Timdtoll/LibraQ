package com.example.libraq.controller;

import com.example.libraq.dto.RegistrationDto;
import com.example.libraq.model.Renter;
import com.example.libraq.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	private final UserService userService;

	public RegistrationController(UserService userService) {
		this.userService = userService;
	}

	//show registration form
	@GetMapping
    public String showForm(Model model) {
        if (!model.containsAttribute("dto")) {
            model.addAttribute("dto", new RegistrationDto());
        }
        return "register.html";   // templates/register.html
    }
	
	// Handle form submission
    @PostMapping
    public String register(@Valid RegistrationDto dto, BindingResult result, Model model) {

        // 1)Validation errors (NotBlank, Email, Size, etc.)
        if (result.hasErrors()) {
            model.addAttribute("dto", dto);
            return "register.html";
        }
        
        public String register(@Valid RegistrationDto dto, BindingResult result, Model model) {

            // 1) Bean Validation errors (NotBlank, Email, Size, etc.)
            if (result.hasErrors()) {
                model.addAttribute("dto", dto);
                return "register.html";
            }

            // 2) Passwords must match
            if (!dto.getPassword().equals(dto.getConfirmPassword())) {
                result.rejectValue("confirmPassword", "mismatch", "Passwords do not match");
                model.addAttribute("dto", dto);
                return "register.html";
            }

            // 3) Email must be unique
            boolean emailExists = !userService.findByEmail(dto.getEmail()).isEmpty();
            if (emailExists) {
                result.rejectValue("email", "duplicate", "Email already registered");
                model.addAttribute("dto", dto);
                return "register.html";
            }

            // 4) Create and save a user (default to Renter)
            Renter user = new Renter(dto.getName(), dto.getEmail(), dto.getPassword());
            userService.addUser(user);

            // 5) On success, go to login
            return "redirect:/login";
        }
    }
