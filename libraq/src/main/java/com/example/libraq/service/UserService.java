package com.example.libraq.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.libraq.model.Users;
import com.example.libraq.repository.UserRepository;

import java.util.List;

@Service
public class UserService implements UserDetailsService{

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

	@Transactional
	public void addUser(Users user) {
		// Hash password before saving
		String hashedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashedPassword);
		userRepo.save(user);
	}

	// Loads user details by email (username) for authentication
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		List<Users> users = userRepo.findByEmail(email);

		if (users.isEmpty()) {
			throw new UsernameNotFoundException("User not found: " + email);
		}

		Users user = users.get(0);
		
		// Build Spring Security UserDetails object
		return User.builder()
			.username(user.getEmail())
			.password(user.getPassword()) // Already hashed from database
			.roles("USER") // Add default role
			.build();
	}
}
