package com.example.library_management.Library.Management.Application.repository;

import com.example.library_management.Library.Management.Application.entity.Book;
import com.example.library_management.Library.Management.Application.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE (:name IS NULL OR u.name LIKE %:name%)")
    Page<User> findUserByName(@Param("name") String name, Pageable pageable);

    //will add the methods later
}
