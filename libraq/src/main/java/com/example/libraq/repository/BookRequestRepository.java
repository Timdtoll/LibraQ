package com.example.libraq.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.libraq.model.BookRequest;
import com.example.libraq.model.RequestStatus;
import com.example.libraq.model.Users;

@Repository
public interface BookRequestRepository extends JpaRepository<BookRequest, Long> {
    List<BookRequest> findByRequestor(Users requestor);
    List<BookRequest> findByStatus(RequestStatus status);
    List<BookRequest> findByRequestDate(LocalDate requestDate);

    Optional<BookRequest> findById(Long id);
}