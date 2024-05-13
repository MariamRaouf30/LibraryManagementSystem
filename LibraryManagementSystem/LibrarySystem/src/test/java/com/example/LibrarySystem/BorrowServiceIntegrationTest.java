package com.example.LibrarySystem;
import com.example.LibrarySystem.model.Book;
import com.example.LibrarySystem.model.Patron;
import com.example.LibrarySystem.service.BorrowService;
import com.example.LibrarySystem.service.BookService;
import com.example.LibrarySystem.service.PatronService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Calendar;
import java.util.Date;

import org.assertj.core.util.DateUtil;

@SpringBootTest
@AutoConfigureTestDatabase
public class BorrowServiceIntegrationTest {

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private BookService bookService;

    @Autowired
    private PatronService patronService;

    @Test
    @Transactional
    @Rollback(false) // Disable rollback to inspect the changes in the database
    public void testBorrowBook() {
        // Given
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2022);
        Date publicationYear = calendar.getTime();
        book.setPublicationYear(publicationYear);
        Book savedBook = bookService.addBook(book);

        Patron patron = new Patron();
        patron.setName("Test Patron");
        patron.setEmail("test@example.com");
        patron.setPhoneNumber("0128508464");
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, 1990);
        Date dob = calendar.getTime();
        patron.setDateOfBirth(dob);
        Patron savedPatron = patronService.addPatron(patron);

        borrowService.borrowBook(savedBook.getId(), savedPatron.getId());
    }
}
