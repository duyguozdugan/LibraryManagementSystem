package com.duyguozdugan.library_management_system.dto.response;

import java.time.LocalDate;

public class LoanResponse {
    private Long loanId;
    private String username;
    private String bookTitle;
    private LocalDate borrowedDate;
    private LocalDate returnDate;

    public LoanResponse(Long loanId, String username, String bookTitle, LocalDate borrowedDate, LocalDate returnDate) {
        this.loanId = loanId;
        this.username = username;
        this.bookTitle = bookTitle;
        this.borrowedDate = borrowedDate;
        this.returnDate = returnDate;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
