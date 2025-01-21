package com.duyguozdugan.library_management_system.service;

import com.duyguozdugan.library_management_system.dto.request.BookRequest;
import com.duyguozdugan.library_management_system.dto.response.BookResponse;
import com.duyguozdugan.library_management_system.model.Book;
import com.duyguozdugan.library_management_system.model.BookStatus;
import com.duyguozdugan.library_management_system.model.Loan;
import com.duyguozdugan.library_management_system.model.User;
import com.duyguozdugan.library_management_system.repository.BookRepository;
import com.duyguozdugan.library_management_system.repository.LoanRepository;
import com.duyguozdugan.library_management_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class BookService {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, LoanRepository loanRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
    }

    // Kitap ekleme
    public void createBook(BookRequest bookRequest) {
        Book book = new Book(bookRequest.getTitle(), bookRequest.getCategory(), bookRequest.getAuthor());
        bookRepository.save(book);
    }


    // Tüm kitapları getirme
    public List<BookResponse> getAllBooks() {

        return bookRepository.findAll().stream().map(book -> {
            BookResponse bookResponse = new BookResponse(book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getCategory(),
                    book.getBookStatus());
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
                book.getBookStatus());
    }

    public void borrowBook(Long bookId, String loggedInUserEmail){
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new IllegalArgumentException("Book not found"));
        if(book.getBookStatus()== BookStatus.BORROWED){
            throw new IllegalArgumentException("Book is already borrowed");
        }

        User user = userRepository.findByEmail(loggedInUserEmail).orElseThrow(() ->
                new IllegalArgumentException("User not found"));

        book.setBookStatus(BookStatus.BORROWED);
        book.setBorrowedBy(user);
        bookRepository.save(book);

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setBorrowedDate(LocalDate.now());
        loanRepository.save(loan);
    }

    public void returnBook(Long bookId, String loggedInUserEmail){
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new IllegalArgumentException("Book not found"));

        if (book.getBookStatus() == BookStatus.AVAILABLE){
            throw new IllegalArgumentException("Book is already available");
        }

        User user = userRepository.findByEmail(loggedInUserEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!book.getBorrowedBy().getId().equals(user.getId())) {
            throw new IllegalStateException("You cannot return a book that you did not borrow.");
        }

        // Kitap durumu güncellenir
        book.setBookStatus(BookStatus.AVAILABLE);
        book.setBorrowedBy(null);
        bookRepository.save(book);

        // Ödünç kaydı güncellenir
        Loan loan = loanRepository.findByBookAndUserAndReturnDateIsNull(book, user)
                .orElseThrow(() -> new IllegalArgumentException("Loan record not found"));
        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);
    }
}
