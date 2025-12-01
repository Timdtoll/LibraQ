package com.example.libraq.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class RemoveBookDto {
    @NotNull(message = "ISBN is required")
    @Min(value = 1000000000L, message = "ISBN must be 10 or 13 digits")
    @Max(value = 9999999999999L, message = "ISBN must be 10 or 13 digits")
    private Long ISBN;

    public RemoveBookDto() {}

    public Long getISBN() {
        return ISBN;
    }

    public void setISBN(Long ISBN) {
        this.ISBN = ISBN;
    }
}