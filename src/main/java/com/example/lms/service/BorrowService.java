package com.example.lms.service;

import com.example.lms.dto.BorrowDTO;

public interface BorrowService {
    public BorrowDTO borrowBook(Long userId, Long bookId);
    public BorrowDTO returnBook(Long borrowingId);
}
