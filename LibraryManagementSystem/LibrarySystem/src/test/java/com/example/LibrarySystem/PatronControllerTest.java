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

import com.example.LibrarySystem.controller.PatronController;
import com.example.LibrarySystem.exception.ApiRequestException;
import com.example.LibrarySystem.model.Patron;
import com.example.LibrarySystem.service.PatronService;

@RunWith(MockitoJUnitRunner.class)
public class PatronControllerTest {

    @Mock
    private PatronService patronService;

    @InjectMocks
    private PatronController patronController;

    private List<Patron> testPatrons;

    @Before
    public void setup() {
        // Setup test data
        testPatrons = Arrays.asList(
            new Patron(1, "Patron 1", "patron1@example.com", "password1", "1234567890", null),
            new Patron(2, "Patron 2", "patron2@example.com", "password2", "0987654321", null)
        );
    }

    @Test
    public void testGetAllPatrons() {
        when(patronService.getAllPatrons()).thenReturn(testPatrons);

        ResponseEntity<List<Patron>> response = patronController.getAllPatrons();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testPatrons, response.getBody());
    }

    @Test
    public void testGetPatronById_ValidId() {
        int validId = 1;
        Patron expectedPatron = testPatrons.get(0);
        when(patronService.getPatronById(validId)).thenReturn(Optional.of(expectedPatron));

        ResponseEntity<Patron> response = patronController.getPatronById(validId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPatron, response.getBody());
    }

    @Test(expected = ApiRequestException.class)
    public void testGetPatronById_InvalidId() {
        Integer invalidId = 100;
        when(patronService.getPatronById(invalidId)).thenReturn(Optional.empty());

        patronController.getPatronById(invalidId);
    }

    @Test
    public void testAddPatron() {
        Patron newPatron = new Patron(null, "New Patron", "newpatron@example.com", "password", "1234567890", null);
        Patron addedPatron = new Patron(3, "New Patron", "newpatron@example.com", "password", "1234567890", null);
        when(patronService.addPatron(any(Patron.class))).thenReturn(addedPatron);

        ResponseEntity<Patron> response = patronController.addPatron(newPatron);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(addedPatron.getId(), response.getBody().getId());
        assertEquals(addedPatron.getName(), response.getBody().getName());
        assertEquals(addedPatron.getEmail(), response.getBody().getEmail());
        assertEquals(addedPatron.getPhoneNumber(), response.getBody().getPhoneNumber());
    }

    @Test
    public void testUpdatePatron_ValidId() {
        Integer validId = 1;
        Patron updatedPatron = new Patron(validId, "Updated Patron", "updatedpatron@example.com", "password", "1234567890", null);
        when(patronService.updatePatron(anyInt(), any(Patron.class))).thenReturn(updatedPatron);

        ResponseEntity<Patron> response = patronController.updatePatron(validId, updatedPatron);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(validId, response.getBody().getId());
        assertEquals(updatedPatron.getName(), response.getBody().getName());
        assertEquals(updatedPatron.getEmail(), response.getBody().getEmail());
        assertEquals(updatedPatron.getPhoneNumber(), response.getBody().getPhoneNumber());
    }

    @Test(expected = ApiRequestException.class)
    public void testUpdatePatron_InvalidId() {
        Integer invalidId = 100;
        Patron updatedPatron = new Patron(invalidId, "Updated Patron", "updatedpatron@example.com", "password", "1234567890", null);
        when(patronService.updatePatron(invalidId, updatedPatron)).thenThrow(ApiRequestException.class);

        patronController.updatePatron(invalidId, updatedPatron);
    }

    @Test
    public void testDeletePatron_ValidId() {
        Integer validId = 1;
        doNothing().when(patronService).deletePatron(validId);

        ResponseEntity<Void> response = patronController.deletePatron(validId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test(expected = ApiRequestException.class)
    public void testDeletePatron_InvalidId() {
        Integer invalidId = 100;
        doNothing().when(patronService).deletePatron(invalidId);

        patronController.deletePatron(invalidId);
    }
}
