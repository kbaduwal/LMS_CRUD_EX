package com.example.lms.controller;

import com.example.lms.dto.BorrowDTO;
import com.example.lms.exception.ResourceNotFoundException;
import com.example.lms.exception.RoleNotFoundException;
import com.example.lms.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @PostMapping("/borrowing")
    public ResponseEntity<BorrowDTO> borrowBook( @RequestParam Long userId,
                                                 @RequestParam Long bookId) {
        try {
            BorrowDTO borrow = borrowService.borrowBook(userId, bookId);
            return ResponseEntity.
                    status(HttpStatus.CREATED).body(borrow);
        }catch (RoleNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    @PostMapping("/return/{borrowId}")
    public ResponseEntity<String> returnBook(@PathVariable Long borrowId) {
        try {
            BorrowDTO borrowing = borrowService.returnBook(borrowId);
            return ResponseEntity.status(HttpStatus.OK).body("Book returned successfully");
        } catch (ResourceNotFoundException e) {
           throw e;
        }
    }
}
