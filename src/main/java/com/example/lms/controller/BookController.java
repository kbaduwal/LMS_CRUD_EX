package com.example.lms.controller;

import com.example.lms.dto.BookDTO;
import com.example.lms.exception.ResourceNotFoundException;
import com.example.lms.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.addBook(bookDTO));
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long id) {
        try {
            BookDTO book = bookService.getBookById(id);
            return ResponseEntity.ok(book);

        } catch (ResourceNotFoundException e) {
           throw e;
        }
    }

    @GetMapping("/available")
    public List<BookDTO> getAvailableBooks() {
        return bookService.getAvailableBooks();
    }
}
