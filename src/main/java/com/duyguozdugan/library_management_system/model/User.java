package com.duyguozdugan.library_management_system.model;

import com.duyguozdugan.library_management_system.dto.request.UserRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User  implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role; // admin, user


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @OneToMany(mappedBy = "borrowedBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> borrowedBooks = new ArrayList<>();



    public void borrowBook(Book book) {
        if (!book.isBorrowed()) {
            book.setBorrowed(true);
            borrowedBooks.add(book);
        } else {
            throw new IllegalStateException("This book is already borrowed.");
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            book.setBorrowed(false);
            borrowedBooks.remove(book);
        } else {
            throw new IllegalStateException("This book is not borrowed by this user.");
        }
    }
    public void updateFrom(UserRequest user) {

        if (user.getUsername() != null) this.username = user.getUsername();
        if (user.getEmail() != null) this.email = user.getEmail();
        if (user.getPassword() != null) this.password = user.getPassword();
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
}
