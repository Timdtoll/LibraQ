package com.example.libraq.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class BookRequest {

    public BookRequest() {
    }
    public BookRequest(String title, String author, Users requestor, String notes) {
        this.title = title;
        this.author = author;
        this.requestor = requestor;
        this.requestDate = LocalDate.now();
        this.status = RequestStatus.PENDING;
        this.notes = notes;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users requestor;
    
    private LocalDate requestDate;
    
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    private String notes;
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public Users getRequestor() {
        return requestor;
    }
    public void setRequestor(Users requestor) {
        this.requestor = requestor;
    }
    public LocalDate getRequestDate() {
        return requestDate;
    }
    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }
    public RequestStatus getStatus() {
        return status;
    }
    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}
