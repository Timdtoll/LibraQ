package com.example.libraq.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.libraq.model.CheckoutReceipt;

public interface CheckoutReceiptRepository extends JpaRepository<CheckoutReceipt, Long> {

    List<CheckoutReceipt> findByUserId(Long userId);

    List<CheckoutReceipt> findByBookISBN(Long bookISBN);

}
