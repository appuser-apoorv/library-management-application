package com.example.library_management.Library.Management.Application.controller;

import com.example.library_management.Library.Management.Application.dto.BookDTO;
import com.example.library_management.Library.Management.Application.dto.PaginatedResponse;
import com.example.library_management.Library.Management.Application.dto.ReturnBookResponse;
import com.example.library_management.Library.Management.Application.service.BookServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Book Management", description = "APIs for managing books")
public class BookController {

    @Autowired
    private BookServiceImpl bookService;


    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieve all books available in the library")
    public ResponseEntity<PaginatedResponse<BookDTO>> getAllBooks(@RequestParam(required = false) String title,
                                                                  @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                                  @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                                  @RequestParam(defaultValue = "id,asc") String[] sort){
        String sortBy = sort.length > 0 ? sort[0] : "id";
        String sortDir = sort.length >1 ? sort[1] : "asc";
        Sort sorting = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sorting);
        Page<BookDTO> books = bookService.getAllBooks(title , pageable);

        PaginatedResponse<BookDTO> response = new PaginatedResponse<>();
        response.setContent(books.getContent());
        response.setCurrentPage(books.getNumber());
        response.setTotalPages(books.getTotalPages());
        response.setTotalElements(books.getTotalElements());
        return ResponseEntity.ok(response);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Add a new book", description = "Add a book to the library")

    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.createBook(bookDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing book", description = " Update a book details")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id , @RequestBody BookDTO bookDTO){
        return ResponseEntity.ok(bookService.updateBook(id , bookDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a  book", description = "Delete a book from the library")
    public ResponseEntity<String> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return  ResponseEntity.ok("Book Deleted Successfully ");
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/{bookId}/issue/{userId}")
    @Operation(summary = "Issue a book to user", description = "Issue  a book to the user ")
    public ResponseEntity<String> issueBook(@PathVariable Long bookId, @PathVariable Long userId) {
        try {
            bookService.issueBook(bookId, userId);
            return ResponseEntity.ok("Book issued successfully.");
        } catch (Exception e) {
            throw new RuntimeException("Unable to issue the book. Please try again.");
        }
    }


    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/{bookId}/return/{userId}")
    @Operation(summary = "Return a book to Library ", description = "User returns a book to library")
    public ResponseEntity<ReturnBookResponse> returnBook(@PathVariable Long bookId, @PathVariable Long userId) {
        try {
            ReturnBookResponse returnBookResponse = bookService.returnBook(bookId, userId);
            return ResponseEntity.ok(returnBookResponse);
        } catch (Exception e) {
            throw new RuntimeException("Unable to return the book. Please try again.");
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/pay-fine/{userId}")
    @Operation(summary = "Paying fine ", description = "User pays fines to the library")
    public ResponseEntity<String> payFine(@PathVariable Long userId) {
        try {
            bookService.payFine(userId);
            return ResponseEntity.ok("Fine paid successfully.");
        } catch (Exception e) {
            throw new RuntimeException("Unable to process the fine payment. Please try again.");
        }
    }

}
