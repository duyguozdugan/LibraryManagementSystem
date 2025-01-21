package com.duyguozdugan.library_management_system.dto.request;

public class LoanRequest {

    private Long bookId; // Kullanıcı hangi kitabı ödünç almak istiyor

    public LoanRequest() {
    }

    public LoanRequest(Long bookId) {
        this.bookId = bookId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
