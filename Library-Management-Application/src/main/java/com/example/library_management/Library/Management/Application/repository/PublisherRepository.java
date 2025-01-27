package com.example.library_management.Library.Management.Application.repository;

import com.example.library_management.Library.Management.Application.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    public Publisher findByName(String name); // In PublisherRepository
    //will add the methods later
}
