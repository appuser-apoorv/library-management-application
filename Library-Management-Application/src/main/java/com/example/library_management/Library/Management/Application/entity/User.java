package com.example.library_management.Library.Management.Application.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "usersCache")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name =  "id")
    private Long id;

    @Column(name = "name" , nullable = false)
    private String name;

    @Column(name = "username" , nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email" , nullable = false, unique = true)
    private String email;

    @Column(name = "expiry_date" , nullable = false)
    private LocalDate expiryDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fine> fines;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IssueRecord> issueRecords;

    public List<IssueRecord> getIssueRecords() {
        return issueRecords;
    }

    public void setIssueRecords(List<IssueRecord> issueRecords) {
        this.issueRecords = issueRecords;
    }

    public List<Fine> getFines() {
        return fines;
    }

    public void setFines(List<Fine> fines) {
        this.fines = fines;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

}
