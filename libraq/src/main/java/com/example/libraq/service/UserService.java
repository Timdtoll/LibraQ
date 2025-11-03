package com.example.libraq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.libraq.model.Users;
import com.example.libraq.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

	private final UserRepository userRepo;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepo = userRepository;
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
		userRepo.save(user);
	}
}
