package com.example.libraq.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {
	// enum Genre {
	// ROMANCE, MYSTERY, CLASSICAL, THRILLER, NONFICTION, FANTASY
	// }//Maybe make genre an enum later since that makes more sense

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ISBN; // unique for every book
	private String title;
	private String author;
	private String genre;

	public Book() {
	}

	public Book(String title, String author, String genre, Long ISBN) {
		this.title = title;
		this.author = author;
		this.genre = genre;

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

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

}