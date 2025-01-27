package com.example.library_management.Library.Management.Application.service;

import com.example.library_management.Library.Management.Application.dto.BookDTO;
import com.example.library_management.Library.Management.Application.dto.ReturnBookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BookService {

    Page<BookDTO> getAllBooks(String title , Pageable pageable);

    BookDTO createBook(BookDTO bookDTO);

    BookDTO updateBook(Long id , BookDTO bookDTO );

    void  deleteBook(Long id);

    public  void issueBook(Long bookId , Long userId );

    public ReturnBookResponse returnBook(Long bookId , Long userId);

    public void payFine(Long userId);


}
