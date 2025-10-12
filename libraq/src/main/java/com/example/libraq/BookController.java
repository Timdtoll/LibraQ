package com.example.libraq;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.libraq.model.Book;

@Controller
@RequestMapping("/books")
public class BookController {
	private final BookRepository repo;

	public BookController(BookRepository repo) {
		this.repo = repo;
	}

	@GetMapping
	public String list(@RequestParam(required = false) String q,
			@RequestParam(required = false, defaultValue = "title") String by, Model model) {

		List<Book> books;
		if (q == null || q.isBlank()) {
			books = repo.findAll();
		} else if ("author".equalsIgnoreCase(by)) {
			books = repo.findByAuthor(q.trim());
		} else {
			books = repo.findByTitle(q.trim());
		}

		model.addAttribute("books", books);
		model.addAttribute("q", q == null ? "" : q);
		model.addAttribute("by", by);
		return "books";
	}

	// Add book (unchanged)
	@PostMapping
	public String add(@RequestParam String title, @RequestParam String author,
			@RequestParam(defaultValue = "1") int copies) {
		if (copies < 1) {
			copies = 1;
		}
	    System.out.println("ADD title=" + title + " author=" + author + " copies=" + copies);

		Book b = new Book(title, author, copies); // title → title, author → author
	    repo.save(b);
	    return "redirect:/books";
	}
}