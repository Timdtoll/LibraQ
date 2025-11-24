package com.example.libraq.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.libraq.model.BookRequest;
import com.example.libraq.model.RequestStatus;
import com.example.libraq.model.Users;
import com.example.libraq.repository.BookRequestRepository;

import jakarta.transaction.Transactional;

@Service
public class BookRequestService {
    private final BookRequestRepository bookRequestRepository;
    public BookRequestService(BookRequestRepository bookRequestRepository) {
        this.bookRequestRepository = bookRequestRepository;
    }
    @Transactional
    public void createRequest(BookRequest bookRequest) {
        bookRequestRepository.save(bookRequest);
    }
    public List<BookRequest> getAllRequests() {
        return bookRequestRepository.findAll();
    }
    public List<BookRequest> getRequestsByRequestor(Users requestor) {
        return bookRequestRepository.findByRequestor(requestor);
    }
    public List<BookRequest> getRequestsByStatus(RequestStatus status) {
        return bookRequestRepository.findByStatus(status);
    }
    public List<BookRequest> getRequestsByRequestDate(LocalDate requestDate) {
        return bookRequestRepository.findByRequestDate(requestDate);
    }

    public BookRequest getById(Long id) {
        return bookRequestRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book request not found with id: " + id));
    }

    @Transactional
    public void approveRequest(BookRequest bookRequest) {
        bookRequest.setStatus(RequestStatus.APPROVED);
        bookRequestRepository.save(bookRequest);
    }

    @Transactional
    public void rejectRequest(BookRequest bookRequest) {
        bookRequest.setStatus(RequestStatus.REJECTED);
        bookRequestRepository.save(bookRequest);
    }

    @Transactional
    public void deleteRequest(BookRequest bookRequest) {
        bookRequestRepository.delete(bookRequest);
    }
}
