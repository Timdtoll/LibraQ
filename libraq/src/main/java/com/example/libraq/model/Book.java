package com.example.libraq.model;

import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ISBN; // unique for every book
	private String title;
	private String author;
	private Genre genre;
	private boolean available = true;

	public Book() {
	}

	public Book(String title, String author, Genre genre, Long ISBN) {
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.ISBN = ISBN;

	}

	public Long getISBN() {
		return ISBN;
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

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

}