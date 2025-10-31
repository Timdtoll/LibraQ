package com.example.libraq.model;
import jakarta.persistence.Entity;

@Entity
public class Librarian {
	//Template for now. Need to figure out what else a librarian needs and what differentiates them from a renter besides the controller
	
	private String name;
	private String email;
	private String password;
	
	public Librarian() {}
	
	public Librarian(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPassword() {
		return this.password;
	}
}