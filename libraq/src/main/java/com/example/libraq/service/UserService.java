package com.example.libraq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.libraq.model.User;
import com.example.libraq.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
	
	private final UserRepository userRepo;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepo = userRepository;
	}
	
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	
	public List<User> findByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	public List<User> findByname(String name) {
		return userRepo.findByName(name);
	}
	
	public void addUser(User user) {
		userRepo.save(user);
	}
}
