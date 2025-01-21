package com.duyguozdugan.library_management_system.controller;

import com.duyguozdugan.library_management_system.dto.request.LoanRequest;
import com.duyguozdugan.library_management_system.dto.response.LoanResponse;
import com.duyguozdugan.library_management_system.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loan")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestBody LoanRequest loanRequest) {
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        loanService.borrowBook(loanRequest, loggedInEmail);
        return ResponseEntity.ok("Book borrowed successfully.");
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/return/{loanId}")
    public ResponseEntity<String> returnBook(@PathVariable Long loanId) {
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        loanService.returnBook(loanId, loggedInEmail);
        return ResponseEntity.ok("Book returned successfully.");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-loans")
    public ResponseEntity<List<LoanResponse>> getUserLoans() {
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(loanService.getUserLoans(loggedInEmail));
    }

    // 📌 Admin tüm ödünç işlemlerini listeliyor
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<LoanResponse>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    // 📌 Admin İçin 14 Gün Gecikmiş Kitapları Listele
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/overdue")
    public List<LoanResponse> getOverdueLoans() {
        return loanService.getOverdueLoans();
    }

}
