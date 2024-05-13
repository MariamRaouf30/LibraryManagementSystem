package com.example.LibrarySystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "book_id") 
    private Book book;

    @ManyToOne
    @JoinColumn(name = "patron_id") 
    private Patron patron;

    private Date borrowDate;
    private Date returnDate; 
}