package com.example.library_management.Library.Management.Application.repository;

import com.example.library_management.Library.Management.Application.entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FineRepository extends JpaRepository<Fine , Long> {

    //will add the methods later
}
