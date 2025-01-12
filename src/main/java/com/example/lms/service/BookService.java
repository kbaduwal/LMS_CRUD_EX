package com.example.lms.service;

import com.example.lms.dto.BookDTO;

import java.util.List;

public interface BookService {
    public BookDTO addBook(BookDTO bookDTO);
    public BookDTO getBookById(Long id);
    public List<BookDTO> getAvailableBooks();
}
