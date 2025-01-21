package com.duyguozdugan.library_management_system.service;

import com.duyguozdugan.library_management_system.dto.request.LoanRequest;
import com.duyguozdugan.library_management_system.dto.response.LoanResponse;
import com.duyguozdugan.library_management_system.error.BookNotFoundException;
import com.duyguozdugan.library_management_system.error.LoanNotFoundException;
import com.duyguozdugan.library_management_system.error.UserNotFoundException;
import com.duyguozdugan.library_management_system.model.Book;
import com.duyguozdugan.library_management_system.model.BookStatus;
import com.duyguozdugan.library_management_system.model.Loan;
import com.duyguozdugan.library_management_system.model.User;
import com.duyguozdugan.library_management_system.repository.BookRepository;
import com.duyguozdugan.library_management_system.repository.LoanRepository;
import com.duyguozdugan.library_management_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    private final UserRepository userRepository;

    private final BookRepository bookRepository;

    public LoanService(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    // 📌 Kitap Ödünç Alma İşlemi
    public void borrowBook(LoanRequest loanRequest, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + userEmail));

        Book book = bookRepository.findById(loanRequest.getBookId())
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + loanRequest.getBookId()));

        if (loanRepository.existsByBookAndReturnDateIsNull(book)) {
            throw new IllegalStateException("Book is already borrowed!");
        }

        book.setBookStatus(BookStatus.BORROWED);
        bookRepository.save(book);

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setBorrowedDate(LocalDate.now());
        loan.setReturnDate(null); // Henüz iade edilmedi

        loanRepository.save(loan);
    }

    // 📌 Kitap İade Etme İşlemi
    public void returnBook(Long loanId, String userEmail) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan record not found with id: " + loanId));

        if (!loan.getUser().getEmail().equals(userEmail)) {
            throw new IllegalStateException("You can only return your own borrowed books!");
        }

        Book book = loan.getBook();
        book.setBookStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);

        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);
    }


    // 📌 Kullanıcının Ödünç Aldığı Kitapları Listeleme
    public List<LoanResponse> getUserLoans(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + userEmail));

        List<Loan> loans = loanRepository.findByUser(user);

        return loans.stream()
                .map(loan -> new LoanResponse(
                        loan.getId(),
                        loan.getUser().getUsername(),
                        loan.getBook().getTitle(),
                        loan.getBorrowedDate(),
                        loan.getReturnDate()
                ))
                .collect(Collectors.toList());
    }

    // 📌 Admin İçin Tüm Ödünç Kitapları Listeleme
    public List<LoanResponse> getAllLoans() {
        List<Loan> loans = loanRepository.findAll();

        return loans.stream()
                .map(loan -> new LoanResponse(
                        loan.getId(),
                        loan.getUser().getUsername(),
                        loan.getBook().getTitle(),
                        loan.getBorrowedDate(),
                        loan.getReturnDate()
                ))
                .collect(Collectors.toList());
    }

    // 📌 14 Günü Geçmiş ve Hâlâ İade Edilmemiş Kitapları Listele
    public List<LoanResponse> getOverdueLoans() {
        LocalDate cutoffDate = LocalDate.now().minusDays(14);
        List<Loan> overdueLoans = loanRepository.findOverdueLoans(cutoffDate);

        return overdueLoans.stream()
                .map(loan -> new LoanResponse(
                        loan.getId(),
                        loan.getUser().getUsername(),
                        loan.getBook().getTitle(),
                        loan.getBorrowedDate(),
                        loan.getReturnDate()
                ))
                .collect(Collectors.toList());
    }


}
