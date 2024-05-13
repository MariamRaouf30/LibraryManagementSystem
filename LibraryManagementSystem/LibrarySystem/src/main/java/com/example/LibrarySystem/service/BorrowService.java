package com.example.LibrarySystem.service;

import com.example.LibrarySystem.model.Book;
import com.example.LibrarySystem.model.Borrow;
import com.example.LibrarySystem.model.Patron;
import com.example.LibrarySystem.repository.BookRepository;
import com.example.LibrarySystem.repository.BorrowRepository;
import com.example.LibrarySystem.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Service
public class BorrowService {
    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    List<Borrow> getAllBorrows(){
        return borrowRepository.findAll();
    }

    @Transactional
    public void borrowBook(Integer bookId, Integer patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book with id " + bookId + " not found"));

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new IllegalArgumentException("Patron with id " + patronId + " not found"));

        // Check if the book is available for borrowing
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book with id " + bookId + " is not available for borrowing");
        }

        // Record the borrowing
        Borrow borrow = new Borrow();
        borrow.setBook(book);
        borrow.setPatron(patron);
        borrow.setBorrowDate(new Date());

        // Update book availability status
        book.setAvailable(false);
        bookRepository.save(book);

        // Save borrowing record
        borrowRepository.save(borrow);
    }

    @Transactional
    public void returnBook(Integer bookId, Integer patronId) {
        Borrow borrow = borrowRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId)
                .orElseThrow(() -> new IllegalArgumentException("Borrow record not found"));

        borrow.setReturnDate(new Date());

        Book book = borrow.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        borrowRepository.save(borrow);
    }
}