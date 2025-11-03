# LibraQ Frontend Documentation

## Overview

The **LibraQ frontend** is built using **Thymeleaf templates** and custom **CSS** for styling.  
It provides a clean, responsive interface for browsing, searching, and filtering books stored in the library database.

---

## Page Structure

### Main Template: `index.html`

This page serves as the home page and includes three main layout sections:

1. **Header**

   - Displays the site name `LibraQ`.
   - Will later include user profile and logout buttons.
   - Currently styled with a deep red accent (`#8E1616`) and light text (`#E0DFD5`).

2. **Main Content Area**

   - Divided into two columns:
     - **Left Sidebar:** Genre filters (checkbox-based).
     - **Right Section:** Search bar and book list display.

3. **Footer**
   - Displays a simple copyright notice.

---

## Search Feature

### Purpose

Allows users to search for books by **title** or **author name**.

### Location

The search bar is positioned **below the header** and **above the book list**.  
It spans the same width as the main content area.

### Behavior

- Submits a GET request to `/home` with a query parameter:
  ```
  /home?query=someText
  ```
- Automatically updates the displayed book list based on the search term.
- Works in combination with genre filters (when selected).

### Implementation (Thymeleaf Snippet)

```html
<div class="search-bar-container">
  <form th:action="@{/home}" method="get" class="search-bar-wide">
    <input
      type="text"
      name="query"
      placeholder="Search by title or author..."
      th:value="${query}"
    />
    <button type="submit">Search</button>
  </form>
</div>
```

---

## Genre Filtering Sidebar

### Purpose

Enables users to filter books by selecting one or multiple genres.  
This replaces a genre search bar since genres are limited and predefined via an enum.

### Implementation

```html
<aside class="genre-sidebar">
  <h3>Filter by Genre</h3>
  <form th:action="@{/home}" method="get">
    <div class="checkbox-group">
      <label th:each="genre : ${genres}">
        <input
          type="checkbox"
          name="genres"
          th:value="${genre}"
          th:checked="${selectedGenres != null and selectedGenres.contains(genre)}"
        />
        <span th:text="${genre.displayName}">Genre</span>
      </label>
    </div>
    <button type="submit" class="filter-btn">Apply Filter</button>
  </form>
</aside>
```

### Behavior

- Sends a list of selected genres as query parameters:
  ```
  /home?genres=FANTASY&genres=THRILLER
  ```
- The backend combines these selections with the search query (if present) for filtered results.

---

## Book List Display

### Purpose

Displays the currently available or filtered books.

### Implementation

```html
<div class="book-list" th:if="${!#lists.isEmpty(books)}">
  <div class="book-card" th:each="book : ${books}">
    <h3 th:text="${book.title}">Book Title</h3>
    <p>
      <strong>Author:</strong> <span th:text="${book.author}">Author Name</span>
    </p>
    <p>
      <strong>Genre:</strong>
      <span th:text="${book.genre.displayName}">Genre</span>
    </p>
  </div>
</div>
```

### Conditional Rendering

If no books are found:

```html
<div th:if="${#lists.isEmpty(books)}" class="no-books">
  <p>No books found.</p>
</div>
```

---

## CSS Styling

### Design Overview

The CSS uses a warm neutral background (`#E0DFD5`) with dark red accents for structure and contrast.

### Key Classes

| Class              | Purpose                                                         |
| ------------------ | --------------------------------------------------------------- |
| `.genre-sidebar`   | Styles the filter sidebar with rounded borders and shadow       |
| `.search-bar-wide` | Defines full-width horizontal search layout                     |
| `.book-list`       | Creates a responsive grid for book cards                        |
| `.book-card`       | Styles each book item with border, padding, and hover animation |
| `.filter-btn`      | Consistent button style for genre filters                       |
| `.no-books`        | Displays message when no results found                          |

### Responsive Design

At screen widths below 900px:

- The sidebar moves above the book list (`order: -1`).
- The layout becomes a single column for mobile usability.

---

## Visual Summary

```
----------------------------------------------------------
| Header: LibraQ                                         |
----------------------------------------------------------
| Sidebar (Genres) | Search Bar + Book Grid              |
----------------------------------------------------------
| Footer: © 2025 LibraQ Library System                   |
----------------------------------------------------------
```

---
