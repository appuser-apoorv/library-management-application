package com.example.library_management.Library.Management.Application.repository;

import com.example.library_management.Library.Management.Application.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author , Long> {

    public Author findByName(String name); // In AuthorRepository

    //will add the methods later
}
