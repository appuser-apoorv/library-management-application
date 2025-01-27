package com.example.library_management.Library.Management.Application.dto;

import com.example.library_management.Library.Management.Application.entity.Author;
import com.example.library_management.Library.Management.Application.entity.Publisher;

public class BookDTO {

    private Long id;
    private String title;
    private int year;
    private String isbn;
    private int available;
    private String authorName;
    private String publisherName;
    private int issueRecordsCount;

    public int getIssueRecordsCount() {
        return issueRecordsCount;
    }

    public void setIssueRecordsCount(int issueRecordsCount) {
        this.issueRecordsCount = issueRecordsCount;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
}
