package com.example.libraq.service;

import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.libraq.model.Librarian;
import com.example.libraq.model.Renter;
import com.example.libraq.model.Users;
import com.example.libraq.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public List<Users> getAllUsers() {
		return userRepo.findAll();
	}

	public List<Users> findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	public List<Users> findByname(String name) {
		return userRepo.findByName(name);
	}

	public Users getById(Long id) {
		return userRepo.getById(id);
	}
 
	@Transactional
	public void addUser(Users user) {
		// Hash password before saving
		String hashedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashedPassword);
		userRepo.save(user);
	}

	/**
	 * Quick helper to create a Librarian account.
	 */
	@Transactional
	public void createLibrarian(String name, String email, String password) {
		if (!findByEmail(email).isEmpty()) {
			throw new IllegalArgumentException("Email already exists: " + email);
		}
		Librarian librarian = new Librarian(name, email, password);
		addUser(librarian);
	}

	// Loads user details by email (username) for authentication
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		List<Users> users = userRepo.findByEmail(email);

		if (users.isEmpty()) {
			throw new UsernameNotFoundException("User not found: " + email);
		}

		Users user = users.get(0);
		String role;
		if (user instanceof Renter) {
			role = "RENTER";
		} else if (user instanceof Librarian) {
			role = "LIBRARIAN";
		} else {
			role = "USER"; // Fallback (shouldn't happen)
		}
		// Build Spring Security UserDetails object
		return User.builder()
				.username(user.getEmail())
				.password(user.getPassword()) // Already hashed from database
				.roles(role) // Add default role
				.build();
	}
}
