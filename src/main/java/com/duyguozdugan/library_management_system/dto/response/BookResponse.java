package com.duyguozdugan.library_management_system.dto.response;

import com.duyguozdugan.library_management_system.model.BookStatus;
import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private String category;
    private BookStatus borrowed;

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

    public BookStatus getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(BookStatus borrowed) {
        this.borrowed = borrowed;
    }

    public BookResponse(Long id, String title, String author, String category, BookStatus borrowed) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.borrowed = borrowed;
    }
}

