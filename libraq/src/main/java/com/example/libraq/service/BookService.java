package com.example.libraq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.libraq.model.Book;
import com.example.libraq.model.Genre;
import com.example.libraq.repository.BookRepository;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repo;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.repo = bookRepository;
    }

    public void clear() {
        repo.deleteAll();
    }

    public List<Book> getAllBooks() {
        return repo.findAll();
    }

    public List<Book> findByAuthor(String author) {
        return repo.findByAuthor(author);
    }

    public List<Book> findByTitle(String title) {
        return repo.findByTitle(title);
    }

    public List<Book> findByGenre(Genre genre) {
        return repo.findByGenre(genre);
    }

    public void removeBook(Long ISBN) {
        repo.deleteById(ISBN);
    }

    public void addBook(Book book) {
        repo.save(book);
    }

    public List<Book> searchBooks(String query, List<Genre> genres) {
        if ((query == null || query.trim().isEmpty()) && (genres == null || genres.isEmpty())) {
            return getAllBooks();
        }

        List<Book> allBooks = getAllBooks();
        return allBooks.stream()
                .filter(book -> (query == null || query.isBlank()
                        || book.getTitle().toLowerCase().contains(query.toLowerCase())
                        || book.getAuthor().toLowerCase().contains(query.toLowerCase())))
                .filter(book -> (genres == null || genres.isEmpty()
                        || genres.contains(book.getGenre())))
                .toList();
    }

}
