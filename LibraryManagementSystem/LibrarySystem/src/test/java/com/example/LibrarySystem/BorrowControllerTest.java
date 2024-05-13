package com.example.LibrarySystem;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.LibrarySystem.controller.BorrowController;
import com.example.LibrarySystem.exception.ApiRequestException;
import com.example.LibrarySystem.model.Book;
import com.example.LibrarySystem.model.Patron;
import com.example.LibrarySystem.service.BorrowService;

@RunWith(MockitoJUnitRunner.class)
public class BorrowControllerTest {

    @Mock
    private BorrowService borrowService;

    @InjectMocks
    private BorrowController borrowController;

    private Patron testPatron;
    private Book testBook;

    @Before
    public void setup() {
        // Setup test data
        testPatron = new Patron(1, "Test Patron", "testpatron@example.com", "password", "1234567890", null);
        testBook = new Book(1, "Book 1", "Author 1", null, "ISBN 1", true);
    }

    @Test
    public void testBorrowBook() {
        doNothing().when(borrowService).borrowBook(any(Integer.class), any(Integer.class));

        ResponseEntity<?> response = borrowController.borrowBook(1, 1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test(expected = ApiRequestException.class)
    public void testBorrowBook_Exception() {
        doThrow(new RuntimeException()).when(borrowService).borrowBook(any(Integer.class), any(Integer.class));


        borrowController.borrowBook(1, 1);
    }

    @Test
    public void testReturnBook() {
        doNothing().when(borrowService).returnBook(any(Integer.class), any(Integer.class));

        ResponseEntity<?> response = borrowController.returnBook(1, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test(expected = ApiRequestException.class)
    public void testReturnBook_Exception() {
        doThrow(new RuntimeException()).when(borrowService).returnBook(any(Integer.class), any(Integer.class));

        borrowController.returnBook(1, 1);
    }
}
