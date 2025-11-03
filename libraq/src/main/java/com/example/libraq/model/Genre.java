package com.example.libraq.model;

public enum Genre {
    ROMANCE("Romance"),
    MYSTERY("Mystery"),
    CLASSICAL("Classical"),
    THRILLER("Thriller"),
    NONFICTION("Non-fiction"),
    FANTASY("Fantasy"),
    YA("Young Adult"),
    SCIFI("Science Fiction");

    private String displayName;

    private Genre(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
