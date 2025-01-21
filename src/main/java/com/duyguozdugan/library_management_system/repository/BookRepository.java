package com.duyguozdugan.library_management_system.repository;

import com.duyguozdugan.library_management_system.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // 1. Kitap başlığına göre arama
    List<Book> findByTitleContainingIgnoreCase(String title);

    // 2. Yazar adına göre arama
    List<Book> findByAuthorContainingIgnoreCase(String author);

    // 3. Kategoriye göre arama
    List<Book> findByCategory(String category);

    // 4. Ödünç alınmamış kitapları listeleme


    // 5. Ödünç alınmış kitapları listeleme



}
