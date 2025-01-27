package com.example.library_management.Library.Management.Application.service;

import com.example.library_management.Library.Management.Application.dto.BookDTO;
import com.example.library_management.Library.Management.Application.dto.ReturnBookResponse;
import com.example.library_management.Library.Management.Application.entity.*;
import com.example.library_management.Library.Management.Application.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FineRepository fineRepository;

    @Override
    public Page<BookDTO> getAllBooks(String title , Pageable pageable) {

        return bookRepository.findBooksByTitle(title,pageable)
                .map(this::convertToDTO);
    }

    public BookDTO createBook(BookDTO bookDTO) {
        Book book = convertToEntity(bookDTO);
        book = bookRepository.save(book);
        return convertToDTO(book);
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Book not found"));
        book.setTitle(bookDTO.getTitle());
        book.setYear(bookDTO.getYear());
        book.setIsbn(bookDTO.getIsbn());
        book.setAvailable(bookDTO.getAvailable());
        book = bookRepository.save(book);
        return convertToDTO(book);
    }

    public void deleteBook(Long id){
        System.out.println("Book deleted successfully ");
        bookRepository.deleteById(id);
        log.atInfo().log("Book Deleted");

    }


    public void issueBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        if (book.getAvailable() <= 0) {
            throw new RuntimeException("Book not available.");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        //  logic for issuing the book
        book.setAvailable(book.getAvailable() - 1);
        bookRepository.save(book);

        // Added logic to track the issued book
        IssueRecord issueRecord = new IssueRecord();
        issueRecord.setBook(book);
        issueRecord.setUser(user);
        issueRecord.setIssueDate(LocalDate.now());
        user.getIssueRecords().add(issueRecord);
        userRepository.save(user);

    }

    public ReturnBookResponse returnBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        //  logic for returning the book
        book.setAvailable(book.getAvailable() + 1);
        bookRepository.save(book);

        IssueRecord issueRecord = user.getIssueRecords().stream()
                .filter(record -> record.getBook().getId().equals(bookId)&& record.getReturnDate()==null)
                .findFirst()
                .orElseThrow(()-> new RuntimeException("No active issue record found for the book"));
        issueRecord.setReturnDate(LocalDate.now());

        // Calculate fine if applicable and associate with the user
        double fineAmount = calculateFine(issueRecord);
        if (fineAmount > 0) {
            Fine fine = new Fine();
            fine.setAmount(fineAmount);
            fine.setPaymentDate(LocalDate.now());
            fine.setUser(user);
            user.getFines().add(fine);
            fineRepository.save(fine);

        }
        userRepository.save(user);

        return new ReturnBookResponse("Book returned successfully with Fine " ,fineAmount);
    }

    public void payFine(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        user.getFines().forEach(fine -> fine.setPaymentDate(LocalDate.now()));
        userRepository.save(user);
    }

    private double calculateFine(IssueRecord issueRecord) {
       LocalDate localDate = LocalDate.now();
       LocalDate expectedReturnDate = issueRecord.getIssueDate().plusWeeks(2);
       Long overDueDays = ChronoUnit.DAYS.between(expectedReturnDate, localDate);
        return overDueDays > 0 ? overDueDays * 5.0 : 0.0; // 5 rupees per day fine
    }

    private BookDTO convertToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setYear(book.getYear());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setAvailable(book.getAvailable());
        bookDTO.setAuthorName(book.getAuthor() != null ? book.getAuthor().getName() : null);
        bookDTO.setPublisherName(book.getPublisher() != null ? book.getPublisher().getName() : null);

        return bookDTO;
    }

    private Book convertToEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setYear(bookDTO.getYear());
        book.setIsbn(bookDTO.getIsbn());
        book.setAvailable(bookDTO.getAvailable());

        Author author = authorRepository.findByName(bookDTO.getAuthorName());
        Publisher publisher = publisherRepository.findByName(bookDTO.getPublisherName());

        book.setAuthor(author);
        book.setPublisher(publisher);

        return book;
    }
}

