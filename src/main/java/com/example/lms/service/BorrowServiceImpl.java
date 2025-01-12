package com.example.lms.service;

import com.example.lms.dto.BorrowDTO;
import com.example.lms.entity.Book;
import com.example.lms.entity.Borrow;
import com.example.lms.entity.User;
import com.example.lms.enums.UserRole;
import com.example.lms.exception.BadRequestException;
import com.example.lms.exception.ResourceNotFoundException;
import com.example.lms.repository.BookRepository;
import com.example.lms.repository.BorrowRepository;
import com.example.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BorrowServiceImpl implements BorrowService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Override
    public BorrowDTO borrowBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()->new ResourceNotFoundException("User", "userId", userId));

        Book book = bookRepository.findById(bookId).orElseThrow(
                ()->new ResourceNotFoundException("Book", "bookId", bookId));

        if(!book.isAvailable()){
            throw new ResourceNotFoundException("Book", "bookId", bookId);
        }

        //Checking user's borrowing limits based on their roles
        int allowedBooks = getAllowedBooks(user.getRole());
        long currentBorrowing = borrowRepository.findByUser(user).stream()
                .filter(b->b.getReturnDate()==null).count();

        if (currentBorrowing >= allowedBooks){
            throw new BadRequestException("Borrowing limit exceeded.");
        }

        //Create borrow entity
        Borrow borrow = new Borrow();
        borrow.setUser(user);
        borrow.setBook(book);
        borrow.setBorrowDate(LocalDateTime.now());
        borrow.setDueDate(LocalDateTime.now().plusDays(getLoanPeriod(user.getRole())));

        //Update Book Availability
        book.setAvailable(false);
        bookRepository.save(book);

        //Save borrow entity
        borrowRepository.save(borrow);

        BorrowDTO borrowDTO = new BorrowDTO();
        borrowDTO.setId(borrow.getId());
        borrowDTO.setUser(borrow.getUser());
        borrowDTO.setBook(borrow.getBook());
        borrowDTO.setBorrowDate(borrow.getBorrowDate());
        borrowDTO.setDueDate(borrow.getDueDate());
        borrowDTO.setReturnDate(borrow.getReturnDate());

        return borrowDTO;
    }

    @Override
    public BorrowDTO returnBook(Long borrowingId) {

        Borrow borrow = borrowRepository.findById(borrowingId).orElseThrow(
                ()->new ResourceNotFoundException("Borrow", "borrowingId", borrowingId)
        );

        if (borrow.getReturnDate() != null) {
            throw new BadRequestException("Book already returned.");
        }

        borrow.setReturnDate(LocalDateTime.now());
        borrow.getBook().setAvailable(true);
        bookRepository.save(borrow.getBook());

        borrow = borrowRepository.save(borrow);

        BorrowDTO borrowDTO = new BorrowDTO();
        borrowDTO.setId(borrow.getId());
        borrowDTO.setUser(borrow.getUser());
        borrowDTO.setBook(borrow.getBook());
        borrowDTO.setBorrowDate(borrow.getBorrowDate());
        borrowDTO.setDueDate(borrow.getDueDate());
        borrowDTO.setReturnDate(borrow.getReturnDate());

        return borrowDTO;
    }

    private int getAllowedBooks(UserRole role) {
        return switch (role) {
            case STUDENT -> 3;
            case TEACHER -> 5;
            case LIBRARIAN -> 10;
        };
    }

    private int getLoanPeriod(UserRole role) {
        return switch (role) {
            case STUDENT -> 7; // days
            case TEACHER -> 10;
            case LIBRARIAN -> 15;
        };
    }
}
