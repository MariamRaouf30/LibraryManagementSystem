package com.example.LibrarySystem.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.LibrarySystem.config.JwtUtils;
import com.example.LibrarySystem.model.Patron;
import com.example.LibrarySystem.service.PatronDetailsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private final PatronDetailsService patronDetailsService;
    @Autowired
    private final JwtUtils jwtUtils;



    @PostMapping("/api/register")
    public ResponseEntity<?> register(@Valid @RequestBody Patron patron) {
        try{
        Patron newPatron = patronDetailsService.registerPatron(patron);
        return ResponseEntity.ok(newPatron); }
        catch (Exception e) {
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("message", e.getMessage());
            errorDetails.put("cause", e.getCause() != null ? e.getCause().getMessage() : "No additional cause information");
            return ResponseEntity.badRequest().body(errorDetails);
        }
    }

    @PostMapping("/api/auth")
    public ResponseEntity<String> authenticate(@RequestBody Patron authenticationRequest) throws Exception{
        String email = authenticationRequest.getEmail();
    String password = authenticationRequest.getPassword();
    
    try {
        if (!patronDetailsService.verifyPatronCredentials(email, password)) {
            throw new BadCredentialsException("Invalid username or password");
        }
        String jwt = jwtUtils.generateToken(email);
        return ResponseEntity.ok(jwt);
    } catch (BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + ex.getMessage());
    }
    }
}


