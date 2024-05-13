package com.example.LibrarySystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.LibrarySystem.controller.BookController;
import com.example.LibrarySystem.exception.ApiRequestException;
import com.example.LibrarySystem.model.Book;
import com.example.LibrarySystem.service.BookService;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private List<Book> testBooks;

    @Before
    public void setup() {
        // Setup test data
        testBooks = Arrays.asList(
            new Book(1, "Book 1", "Author 1", null, "ISBN 1", true),
            new Book(2, "Book 2", "Author 2", null, "ISBN 2", true)
        );
    }

    @Test
    public void testGetAllBooks() {
        when(bookService.getAllBooks()).thenReturn(testBooks);

        ResponseEntity<List<Book>> response = bookController.getAllBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testBooks, response.getBody());
    }

    @Test
    public void testGetBookById_ValidId() {
        int validId = 1;
        Book expectedBook = testBooks.get(0);
        when(bookService.getBookById(validId)).thenReturn(Optional.of(expectedBook));

        ResponseEntity<Book> response = bookController.getBookById(validId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBook, response.getBody());
    }

    @Test(expected = ApiRequestException.class)
    public void testGetBookById_InvalidId() {
        int invalidId = 100;
        when(bookService.getBookById(invalidId)).thenReturn(Optional.empty());

        bookController.getBookById(invalidId);
    }

    @Test
    public void testAddBook() {
        Book newBook = new Book(null, "New Book", "New Author", null, "New ISBN", true);
        Book addedBook = new Book(3, "New Book", "New Author", null, "New ISBN", true);
        when(bookService.addBook(any(Book.class))).thenReturn(addedBook);

        ResponseEntity<Book> response = bookController.addBook(newBook);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(addedBook.getId(), response.getBody().getId());
        assertEquals(addedBook.getTitle(), response.getBody().getTitle());
        assertEquals(addedBook.getAuthor(), response.getBody().getAuthor());
        assertEquals(addedBook.getIsbn(), response.getBody().getIsbn());
    }

    @Test
    public void testUpdateBook_ValidId() {
        Integer validId = 1;
        Book updatedBook = new Book(validId, "Updated Book", "Updated Author", null, "Updated ISBN", true);
        when(bookService.updateBook(anyInt(), any(Book.class))).thenReturn(updatedBook);

        ResponseEntity<Book> response = bookController.updateBook(validId, updatedBook);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(validId, response.getBody().getId());
        assertEquals(updatedBook.getTitle(), response.getBody().getTitle());
        assertEquals(updatedBook.getAuthor(), response.getBody().getAuthor());
        assertEquals(updatedBook.getIsbn(), response.getBody().getIsbn());
    }

    @Test(expected = ApiRequestException.class)
    public void testUpdateBook_InvalidId() {
        int invalidId = 100;
        Book updatedBook = new Book(invalidId, "Updated Book", "Updated Author", null, "Updated ISBN", true);
        when(bookService.updateBook(invalidId, updatedBook)).thenThrow(ApiRequestException.class);

        bookController.updateBook(invalidId, updatedBook);
    }

    @Test
    public void testDeleteBook_ValidId() {
        int validId = 1;
        when(bookService.bookExists(validId)).thenReturn(true);

        doNothing().when(bookService).deleteBook(validId);
    
        ResponseEntity<Void> response = bookController.deleteBook(validId);
    
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test(expected = ApiRequestException.class)
    public void testDeleteBook_InvalidId() {
        int invalidId = 100;
        bookController.deleteBook(invalidId);
    }
}
