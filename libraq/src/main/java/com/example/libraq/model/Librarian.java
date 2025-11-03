package com.example.libraq.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("LIBRARIAN")
public class Librarian extends Users {
	// Template for now. Need to figure out what else a librarian needs and what
	// differentiates them from a renter besides the controller

	protected Librarian() {
		this.userType = "Librarian";
	}

	public Librarian(String name, String email, String password) {
		super(name, email, password);
	}

}