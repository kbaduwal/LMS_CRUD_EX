package com.example.lms.service;

import com.example.lms.dto.BookDTO;
import com.example.lms.entity.Book;
import com.example.lms.exception.ResourceNotFoundException;
import com.example.lms.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookDTO addBook(BookDTO bookDTO) {

        Long longGeneratedISBN = generateISBN();
        String generatedISBN = Long.toString(longGeneratedISBN);

        // Create and set properties for the Book entity
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(generatedISBN);
        book.setAvailable(bookDTO.isAvailable());

        // Save and retrieve the book to the database(entity)
        book = bookRepository.save(book);


        // Convert the saved Book entity back to a BookDTO
        BookDTO savedBookDTO = new BookDTO();
        savedBookDTO.setTitle(book.getTitle());
        savedBookDTO.setAuthor(book.getAuthor());
        savedBookDTO.setIsbn(book.getIsbn());
        savedBookDTO.setAvailable(book.isAvailable());

        return savedBookDTO;
    }


    @Override
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Book", "id", id));

        return new BookDTO(
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.isAvailable()
        );
    }

    @Override
    public List<BookDTO> getAvailableBooks() {
        //Fetching the available books
        List<Book> availableBooks = bookRepository.findByIsAvailable(true);

        //Convert List of Book to a List of BookDTOs
        List<BookDTO> availableBookDTOs = availableBooks.stream()
                .map(
                        book -> {
                            BookDTO bookDTO = new BookDTO();
                            bookDTO.setTitle(book.getTitle());
                            bookDTO.setAuthor(book.getAuthor());
                            bookDTO.setIsbn(book.getIsbn());
                            bookDTO.setAvailable(book.isAvailable());
                            return bookDTO;
                        }
                ).collect(Collectors.toList());

        return availableBookDTOs;
    }

    public static long generateISBN() {
        return 1_000_000_000L + (long) (Math.random() * 9_000_000_000L);
    }

}
