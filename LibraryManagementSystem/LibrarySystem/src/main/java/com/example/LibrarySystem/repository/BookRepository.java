package com.example.LibrarySystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.LibrarySystem.model.Book;
import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book,Integer>{
    Optional<Book> findById(Integer id);
}