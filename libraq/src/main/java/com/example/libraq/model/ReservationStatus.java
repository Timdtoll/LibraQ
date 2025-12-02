package com.example.libraq.model;


public enum ReservationStatus {
    ACTIVE,        // waiting in queue
    HOLD_READY,    // book returned, user notified
    FULFILLED,     // user checked out the book
    EXPIRED        // hold expired after 3 days
}
