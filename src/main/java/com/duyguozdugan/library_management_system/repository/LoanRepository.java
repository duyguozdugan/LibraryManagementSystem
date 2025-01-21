package com.duyguozdugan.library_management_system.repository;

import com.duyguozdugan.library_management_system.model.Book;
import com.duyguozdugan.library_management_system.model.Loan;
import com.duyguozdugan.library_management_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

     // Kitap zaten ödünç alınmış mı kontrol eder (iade edilmemişse)
     boolean existsByBookAndReturnDateIsNull(Book book);

     // Kullanıcının ödünç aldığı tüm kitapları getirir
     List<Loan> findByUser(User user);
     Optional<Loan> findByBookAndUserAndReturnDateIsNull(Book book, User user);

     // 14 günü geçmiş ve iade edilmemiş kitapları getir
     @Query("SELECT l FROM Loan l WHERE l.returnDate IS NULL AND l.borrowedDate < :cutoffDate")
     List<Loan> findOverdueLoans(LocalDate cutoffDate);
}
