package com.example.libraq.model;
import jakarta.persistence.Entity;

@Entity
public class Renter extends User {
	/*
	 * TODO:
	 * Add a field for checked out books. Need to figure out how we are storing them I.E txt file, arrayList etc...
	 * Add a field and appropriate methods for amount of money due
	 * 
	 */
	private Long id;
	private String userType = "Renter";
	private String name;
	private String email;
	private String password;
	
	public Renter(String name, String email, String password) {
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