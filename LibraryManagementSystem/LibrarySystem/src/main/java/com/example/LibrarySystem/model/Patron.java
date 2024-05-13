package com.example.LibrarySystem.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@NoArgsConstructor
@AllArgsConstructor
@Data
// ID, name, contact information
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(unique=true)
    @Email(message = "Please provide a valid email address")
    private String email;
    private String password;
    private String phoneNumber;
    private Date dateOfBirth;
}
