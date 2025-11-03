package com.example.libraq.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("RENTER")
public class Renter extends Users {
	/*
	 * TODO:
	 * Add a field for checked out books. Need to figure out how we are storing them
	 * I.E txt file, arrayList etc...
	 * Add a field and appropriate methods for amount of money due
	 * 
	 */

	protected Renter() {
		userType = "Renter";
	}

	public Renter(String name, String email, String password) {
		super(name, email, password);
	}

}