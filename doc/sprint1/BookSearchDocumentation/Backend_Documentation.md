# LibraQ Backend Documentation

## Overview
The **backend system** of LibraQ is built with **Spring Boot** using an MVC architecture.  
It manages book data retrieval, filtering, and delivery to the frontend via Thymeleaf templates.

---

## Architecture
```
┌──────────────────────┐
│      Controller      │  →  Handles requests from the UI (/home)
├──────────────────────┤
│       Service        │  →  Contains business logic (search, filter)
├──────────────────────┤
│     Repository       │  →  Data access (BookRepository)
├──────────────────────┤
│        Model         │  →  Defines Book entity and Genre enum
└──────────────────────┘
```

---

## Main Components

### 1. `Book`
A standard entity class that represents a book in the system.

```java
public class Book {
    private Long id;
    private String title;
    private String author;
    private Genre genre;
    // getters, setters, constructors
}
```

---

### 2. `Genre` (Enum)
Defines the set of allowed book genres.

```java
public enum Genre {
    ROMANCE("Romance"),
    MYSTERY("Mystery"),
    CLASSICAL("Classical"),
    THRILLER("Thriller"),
    NONFICTION("Non-fiction"),
    FANTASY("Fantasy"),
    YA("Young Adult"),
    SCIFI("Science Fiction");

    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
```

---

### 3. `BookRepository`
Responsible for data persistence and retrieval.  
For in-memory or JPA repositories, it might extend `CrudRepository<Book, Long>` or `JpaRepository<Book, Long>`.

---

### 4. `BookService`
Contains the main application logic.

#### Key Methods
```java
public List<Book> getAllBooks();
public long countBooks();
public void addBook(Book book);
public List<Book> findByTitle(String title);
public List<Book> findByAuthor(String author);
public List<Book> searchBooks(String query, List<Genre> genres);
```

#### Search & Filter Logic
```java
public List<Book> searchBooks(String query, List<Genre> genres) {
    if ((query == null || query.isBlank()) && (genres == null || genres.isEmpty())) {
        return getAllBooks();
    }

    return getAllBooks().stream()
        .filter(book -> (query == null || query.isBlank()
            || book.getTitle().toLowerCase().contains(query.toLowerCase())
            || book.getAuthor().toLowerCase().contains(query.toLowerCase())))
        .filter(book -> (genres == null || genres.isEmpty()
            || genres.contains(book.getGenre())))
        .toList();
}
```

---

### 5. `HomeController`
Handles all user-facing routes for the library interface.

#### Route: `/home`
```java
@GetMapping("/home")
public String home(
        @RequestParam(value = "query", required = false) String query,
        @RequestParam(value = "genres", required = false) List<Genre> selectedGenres,
        Model model) {

    List<Book> books;

    if ((query != null && !query.trim().isEmpty()) || (selectedGenres != null && !selectedGenres.isEmpty())) {
        books = bookService.searchBooks(query, selectedGenres);
    } else {
        books = bookService.getAllBooks();
    }

    model.addAttribute("genres", Genre.values());
    model.addAttribute("selectedGenres", selectedGenres);
    model.addAttribute("books", books);
    model.addAttribute("query", query);

    return "index";
}
```

#### Responsibilities
- Fetches books based on title/author search and genre filters.
- Passes all necessary data (`books`, `genres`, `query`) to the Thymeleaf view.
- Maintains state of selected checkboxes after filtering.

---

## Data Initialization

During startup, the system can automatically preload several books into the repository using `@PostConstruct` or a `CommandLineRunner`:

```java
@PostConstruct
public void initialize() {
    if (bookService.countBooks() == 0) {
        bookService.addBook(new Book("1984", "George Orwell", Genre.SCIFI));
        bookService.addBook(new Book("Pride and Prejudice", "Jane Austen", Genre.ROMANCE));
        bookService.addBook(new Book("The Hobbit", "J.R.R. Tolkien", Genre.FANTASY));
    }
}
```

---

## Request Flow Summary
```
User Action → Controller → Service → Repository → Model → Thymeleaf → UI
```

### Example:
1. User selects “Fantasy” and types “Rowling”.
2. Controller receives `/home?query=Rowling&genres=FANTASY`
3. Service filters data by both parameters.
4. Repository provides the base data.
5. Thymeleaf renders the updated book list dynamically.

---

## Backend Highlights
- Built on **Spring Boot MVC**.
- Uses **Thymeleaf** for server-side rendering.
- Supports combined search and filtering logic.
- Clean separation between controller, service, and model layers.
- Easily extendable for authentication, pagination, or REST API endpoints.
