package com.example.libraq.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.libraq.model.Book;
import com.example.libraq.service.BookService;

@Controller
@RequestMapping("/books")
public class BookController {
	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping
	public String list(@RequestParam(required = false) String q,
			@RequestParam(required = false, defaultValue = "title") String by, Model model) {

		// List<Book> books;
		// if (q == null || q.isBlank()) {
		// books = bookService.getAllBooks();
		// } else if ("author".equalsIgnoreCase(by)) {
		// books = bookService.findByAuthor(q.trim());
		// } else {
		// books = bookService.findByTitle(q.trim());
		// }

		// model.addAttribute("books", books);
		model.addAttribute("q", q == null ? "" : q);
		model.addAttribute("by", by);
		return "books.html";
	}

	// Add book (unchanged)
	@PostMapping
	public String add(@RequestParam String title, @RequestParam String author,
			@RequestParam String genre) {
		System.out.println("ADD title=" + title + " author=" + author + " genre=" +
				genre);

		Book b = new Book(title, author, genre); // title → title, author → author
		bookService.addBook(b);
		return "redirect:/books";
	}
}