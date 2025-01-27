package com.example.library_management.Library.Management.Application.repository;

import com.example.library_management.Library.Management.Application.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE (:title IS NULL OR b.title LIKE %:title%)")
    Page<Book> findBooksByTitle(@Param("title") String title, Pageable pageable);


    //will add the methods later
}
