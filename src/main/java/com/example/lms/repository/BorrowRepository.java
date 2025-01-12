package com.example.lms.repository;

import com.example.lms.entity.Borrow;
import com.example.lms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    List<Borrow> findByUser(User user);
    List<Borrow> findByReturnDateIsNull();
}
