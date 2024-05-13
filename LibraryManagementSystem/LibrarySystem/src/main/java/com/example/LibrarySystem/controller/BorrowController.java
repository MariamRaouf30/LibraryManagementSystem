package com.example.LibrarySystem.controller;

import com.example.LibrarySystem.exception.ApiRequestException;
import com.example.LibrarySystem.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<?> borrowBook(@PathVariable Integer bookId, @PathVariable Integer patronId) {
         try {
            borrowService.borrowBook(bookId, patronId);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new ApiRequestException("Error borrowing book", ex);
        }
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<?> returnBook(@PathVariable Integer bookId, @PathVariable Integer patronId) {
        try {
            borrowService.returnBook(bookId, patronId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApiRequestException("Error returning book", ex);
        }
    }
}
