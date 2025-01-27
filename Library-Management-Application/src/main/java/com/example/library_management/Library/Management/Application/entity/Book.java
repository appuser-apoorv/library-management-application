package com.example.library_management.Library.Management.Application.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;

@Entity
@Table(name = "books")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE , region = "bookCache")
public class Book {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "year" , nullable = false)
    private int year;

    @Column(name = "isbn" , nullable = false )
    private String isbn;

    @Column(name = "available" , nullable = false)
    private int available;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name= "author_id")
    private Author author;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;


    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IssueRecord> issueRecords;

    public List<IssueRecord> getIssueRecords() {
        return issueRecords;
    }

    public void setIssueRecords(List<IssueRecord> issueRecords) {
        this.issueRecords = issueRecords;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    @Transient
    @JsonProperty("authorName")
    public String getAuthorName() {
        return author != null ? author.getName() : null;
    }

    @Transient
    @JsonProperty("publisherName")
    public String getPublisherName() {
        return publisher != null ? publisher.getName() : null;
    }
}
