package com.example.LibrarySystem.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.LibrarySystem.model.Book;
import com.example.LibrarySystem.repository.BookRepository;

// ● GET /api/books: Retrieve a list of all books.
// ● GET /api/books/{id}: Retrieve details of a specific book by ID.
// ● POST /api/books: Add a new book to the library.
// ● PUT /api/books/{id}: Update an existing book's information.
// ● DELETE /api/books/{id}: Remove a book from the library.
@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Integer id) {
        return bookRepository.findById(id);
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Integer id, Book book) {
        if (bookRepository.existsById(id)) {
            book.setId(id);
            return bookRepository.save(book);
        } else {
            throw new IllegalArgumentException("Book with id " + id + " does not exist");
        }
    }

    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

    public boolean bookExists(Integer id){
        Optional<Book> book = bookRepository.findById(id);
            if (book.isPresent())
                return true;
            return false;
    }
}
