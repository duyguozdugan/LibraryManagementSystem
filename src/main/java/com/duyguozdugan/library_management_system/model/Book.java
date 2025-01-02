package com.duyguozdugan.library_management_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;


@Entity
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String category;
    private boolean borrowed = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User borrowedBy;

    public Book() {
    }


    public Book(Long id, String title, String author, String category, boolean borrowed, User borrowedBy) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.borrowed = borrowed;
        this.borrowedBy = borrowedBy;
    }

    public Book(String title,String category ,String author) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.borrowed = false;
    }


    public void updateFrom(Book book) {
        if (book.getTitle() != null) this.title = book.getTitle();
        if (book.getAuthor() != null) this.author = book.getAuthor();
        if (book.getCategory() != null) this.category = book.getCategory();
        this.borrowed = book.isBorrowed();
        this.borrowedBy = book.getBorrowedBy();
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    public User getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(User borrowedBy) {
        this.borrowedBy = borrowedBy;
    }
}
