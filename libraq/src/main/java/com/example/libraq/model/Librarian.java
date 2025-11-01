package com.example.libraq.model;
import jakarta.persistence.Entity;

@Entity
public class Librarian extends User {
	//Template for now. Need to figure out what else a librarian needs and what differentiates them from a renter besides the controller
	
	private Long id;
	private String userType = "Librarian";
	private String name;
	private String email;
	private String password;
	
	public Librarian(String name, String email, String password) {
		super(name, email, password);
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
	
	public String getUserType() {
		return this.userType;
	}
}