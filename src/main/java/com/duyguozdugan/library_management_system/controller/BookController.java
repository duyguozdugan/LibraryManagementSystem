package com.duyguozdugan.library_management_system.controller;

import com.duyguozdugan.library_management_system.dto.request.BookRequest;
import com.duyguozdugan.library_management_system.dto.response.BookResponse;
import com.duyguozdugan.library_management_system.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")

public class BookController {

    private  BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<String> createBook(@RequestBody BookRequest bookRequest) {
        bookService.createBook(bookRequest);
        return ResponseEntity.ok("Book added successfully.");
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        BookResponse book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully.");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest) {
        bookService.updateBook(id, bookRequest);
        return ResponseEntity.ok("Book updated successfully.");
    }

    @PostMapping("/{bookId}/borrow")
    public ResponseEntity<?> borrowBook(@PathVariable Long bookId) {
        String loggedInUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        bookService.borrowBook(bookId, loggedInUserEmail);
        return ResponseEntity.ok("Book borrowed successfully.");
    }

    @PostMapping("/{bookId}/return")
    public ResponseEntity<?> returnBook(@PathVariable Long bookId) {
        String loggedInUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        bookService.returnBook(bookId, loggedInUserEmail);
        return ResponseEntity.ok("Book returned successfully.");
    }

}


