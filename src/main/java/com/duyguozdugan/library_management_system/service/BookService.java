package com.duyguozdugan.library_management_system.service;

import com.duyguozdugan.library_management_system.dto.request.BookRequest;
import com.duyguozdugan.library_management_system.dto.response.BookResponse;
import com.duyguozdugan.library_management_system.model.Book;
import com.duyguozdugan.library_management_system.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class BookService {

    private  BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Kitap ekleme
    public void createBook(BookRequest bookRequest) {
        Book book = new Book(bookRequest.getTitle(),bookRequest.getCategory(), bookRequest.getAuthor());
        bookRepository.save(book);
    }


    // Tüm kitapları getirme
    public List<BookResponse> getAllBooks() {

        return bookRepository.findAll().stream().map(book -> {
            BookResponse bookResponse = new BookResponse(book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getCategory(),
                    book.isBorrowed());
            return bookResponse;

        }).collect(Collectors.toList());
    }

    // Kitap silme
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // Kitap güncelleme
    public void updateBook(Long id, BookRequest bookRequest) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Book not found"));
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setCategory(bookRequest.getCategory());
        bookRepository.save(book);
    }

    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Book not found"));
        return new BookResponse(book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getCategory(),
                book.isBorrowed());
    }
}
