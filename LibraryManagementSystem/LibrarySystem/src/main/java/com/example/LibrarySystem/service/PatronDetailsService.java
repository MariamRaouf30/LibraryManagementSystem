package com.example.LibrarySystem.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.LibrarySystem.model.Patron;
import com.example.LibrarySystem.repository.PatronRepository;

@Service
public class PatronDetailsService implements UserDetailsService {

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Patron> patronOptional = patronRepository.findByEmail(email);
        if (patronOptional.isEmpty()) {
            throw new UsernameNotFoundException("No user found with email: " + email);
        }
        Patron patron = patronOptional.get();
        
        // Assuming you have a custom UserDetails implementation for Patron
        return new org.springframework.security.core.userdetails.User(
                patron.getEmail(),
                patron.getPassword(),
                // You may need to add roles and authorities here if Patron has them
                Collections.emptyList()
        );
        }

    public Patron registerPatron(Patron patron) {
        patron.setPassword(passwordEncoder.encode(patron.getPassword())); // Encode the password
        return patronRepository.save(patron);
    }
    public boolean verifyPatronCredentials(String email, String password) {
        Optional<Patron> patronOptional = patronRepository.findByEmail(email);
        if (patronOptional.isPresent()) {
            Patron patron = patronOptional.get();
            return passwordEncoder.matches(password, patron.getPassword());
        }
        return false;
    }
}