# LibraryManagementSystem

# Project Description:
The Library Management System API built with Spring Boot allows librarians to manage books, patrons, and borrowing records efficiently. This system provides RESTful endpoints for various operations related to book and patron management, as well as borrowing transactions.

## Entities:

Book: Represents a book in the library and includes attributes such as ID, title, author, publication year, ISBN.
Patron: Represents a library patron and contains details like ID, name, contact information.
Borrowing Record: Tracks the association between books and patrons, including borrowing and return dates.

## API Endpoints:

### Book Management Endpoints:
GET /api/books: Retrieve a list of all books.
GET /api/books/{id}: Retrieve details of a specific book by ID.
POST /api/books: Add a new book to the library.
PUT /api/books/{id}: Update an existing book's information.
DELETE /api/books/{id}: Remove a book from the library.
### Patron Management Endpoints:
GET /api/patrons: Retrieve a list of all patrons.
GET /api/patrons/{id}: Retrieve details of a specific patron by ID.
POST /api/patrons: Add a new patron to the system.
PUT /api/patrons/{id}: Update an existing patron's information.
DELETE /api/patrons/{id}: Remove a patron from the system.
### Borrowing Endpoints:
POST /api/borrow/{bookId}/patron/{patronId}: Allow a patron to borrow a book.
PUT /api/return/{bookId}/patron/{patronId}: Record the return of a borrowed book by a patron.

## Data Storage:

The application uses H2 database to persist book, patron, and borrowing record details.
Proper relationships are set up between entities, such as one-to-many between books and borrowing records.

## Validation and Error Handling:

Input validation is implemented for API requests to ensure data integrity.
Exceptions are handled gracefully, and appropriate HTTP status codes and error messages are returned.

## Security :

JWT-based authorization is implemented to protect the API endpoints for enhanced security.

## Aspects :

Logging using Aspect-Oriented Programming (AOP) is implemented to log method calls, exceptions, and performance metrics for certain operations like book additions, updates, and patron transactions.

## Transaction Management:

Declarative transaction management using Spring's @Transactional annotation is implemented to ensure data integrity during critical operations.

## Testing:

Unit tests are written to validate the functionality of API endpoints using testing frameworks like JUnit and Mockito.

# How to Run:

Clone the repository from GitHub.
Navigate to the project directory.
Build the project using Maven: mvn clean install.
Run the application: mvn spring-boot:run.
Access the API endpoints using tools like Postman or cURL.
Interacting with API Endpoints:

Use the provided endpoints to perform CRUD operations on books and patrons.
Use the borrowing endpoints to manage borrowing transactions between books and patrons.
