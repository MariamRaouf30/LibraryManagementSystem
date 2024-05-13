package com.example.LibrarySystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.LibrarySystem.model.Patron;

@Repository
public interface PatronRepository extends JpaRepository<Patron,Integer>{
    List<Patron> findAll();
    Optional<Patron> findById(Integer id);
    Optional<Patron> findByEmail(String email);
    void deleteById(Integer id);
}