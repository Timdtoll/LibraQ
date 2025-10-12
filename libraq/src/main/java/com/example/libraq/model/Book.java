package com.example.libraq.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String author;
	private int totalCopies;
	private int availableCopies;

	public Book() {
	}

	public Book(String title, String author, int copies) {
		this.title = title;
		this.author = author;
		this.totalCopies = copies;
		this.availableCopies = copies;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String t) {
		this.title = t;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String a) {
		this.author = a;
	}

	public int getTotalCopies() {
		return totalCopies;
	}

	public void setTotalCopies(int c) {
		this.totalCopies = c;
	}

	public int getAvailableCopies() {
		return availableCopies;
	}

	public void setAvailableCopies(int c) {
		this.availableCopies = c;
	}
}