package com.example.lms.repository;

import com.example.lms.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByIsAvailable(boolean isAvailable);
}
