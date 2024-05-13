package com.example.LibrarySystem.controller;

import com.example.LibrarySystem.exception.ApiRequestException;
import com.example.LibrarySystem.model.Book;
import com.example.LibrarySystem.model.Patron;
import com.example.LibrarySystem.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {
    @Autowired
    private PatronService patronService;

    @GetMapping
    public ResponseEntity<List<Patron>> getAllPatrons() {
        try {
            List<Patron> patrons = patronService.getAllPatrons();
            return new ResponseEntity<>(patrons, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApiRequestException("Error retrieving patrons", ex);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable Integer id) {
        try {
            Optional<Patron> patron = patronService.getPatronById(id);
            if (patron.isPresent()) {
                return new ResponseEntity<>(patron.get(), HttpStatus.OK);
            } else {
                throw new ApiRequestException("Patron not found with id: " + id);
            }
        } catch (Exception ex) {
            throw new ApiRequestException("Error retrieving book with id: " + id, ex);
        }
    }

    @PostMapping
    public ResponseEntity<Patron> addPatron(@RequestBody Patron patron) {
        try {
            Patron addedPatron = patronService.addPatron(patron);
            return new ResponseEntity<>(addedPatron, HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new ApiRequestException("Error adding patron", ex);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable Integer id, @RequestBody Patron patron) {
        try {
            Patron updatedPatron = patronService.updatePatron(id, patron);
            return new ResponseEntity<>(updatedPatron, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApiRequestException("Error updating patron with id: " + id, ex);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable Integer id) {
        boolean patronExists = patronService.patronExists(id);
        if (!patronExists) {
            throw new ApiRequestException("Patron with ID " + id + " not found");
        }
        try {
            patronService.deletePatron(id);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            throw new ApiRequestException("Error deleting patron with id: " + id, ex);
        }
    }
}
