package com.example.libraq.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.libraq.model.Book;
import com.example.libraq.model.CheckoutReceipt;
import com.example.libraq.model.Users;

public interface CheckoutReceiptRepository extends JpaRepository<CheckoutReceipt, Long> {

    List<CheckoutReceipt> findByUserId(Long userId);

    List<CheckoutReceipt> findByUser(Users user);

    List<CheckoutReceipt> findByBookISBN(Long bookISBN);

    List<CheckoutReceipt> findByReturnDateIsNull();

    Optional<CheckoutReceipt> findByBookAndReturnDateIsNull(Book book);

    Optional<CheckoutReceipt> findById(Long id);

}
