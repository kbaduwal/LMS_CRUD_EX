package com.example.lms.service;

import com.example.lms.dto.BorrowDTO;
import com.example.lms.exception.RoleNotFoundException;

public interface BorrowService {
    public BorrowDTO borrowBook(Long userId, Long bookId) throws RoleNotFoundException;
    public BorrowDTO returnBook(Long borrowingId);
}
