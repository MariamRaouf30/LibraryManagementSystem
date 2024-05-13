package com.example.LibrarySystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.LibrarySystem.exception.ApiExceptionHandler;
import com.example.LibrarySystem.exception.ApiRequestException;
import com.example.LibrarySystem.model.Book;
import com.example.LibrarySystem.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;


    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            List<Book> books = bookService.getAllBooks();
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApiRequestException("Error retrieving books", ex);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        try {
            Optional<Book> book = bookService.getBookById(id);
            if (book.isPresent()) {
                return new ResponseEntity<>(book.get(), HttpStatus.OK);
            } else {
                throw new ApiRequestException("Book not found with id: " + id);
            }
        } catch (Exception ex) {
            throw new ApiRequestException("Error retrieving book with id: " + id, ex);
        }
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        try {
            Book addedBook = bookService.addBook(book);
            return new ResponseEntity<>(addedBook, HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new ApiRequestException("Error adding book", ex);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer id, @RequestBody Book book) {
        try {
            Book updatedBook = bookService.updateBook(id, book);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApiRequestException("Error updating book with id: " + id, ex);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        boolean bookExists = bookService.bookExists(id);
        if (bookExists) {
            try {
                bookService.deleteBook(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception ex) {
                throw new ApiRequestException("Error deleting book with id: " + id, ex);
            }
        } else {
            throw new ApiRequestException("Book with id " + id + " does not exist");
        }
    }
}