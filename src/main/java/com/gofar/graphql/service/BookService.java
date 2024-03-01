package com.gofar.graphql.service;

import com.gofar.graphql.exception.BookException;
import com.gofar.graphql.entity.Book;
import com.gofar.graphql.repository.AuthorRepository;
import com.gofar.graphql.repository.BookRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {

    private final Logger logger = LoggerFactory.getLogger(BookService.class);
    private BookRepository bookRepository;

    private AuthorRepository authorRepository;

    private final static String BOOK_NOT_FOUND = "Book with id %s not found";
    private final static String BOOK_ALREADY_EXISTS = "Book with id %s does not exists";

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElseThrow(() -> new BookException(String.format(BOOK_NOT_FOUND, id), ErrorType.NOT_FOUND));
    }

    public Book updateBook(Long id, Book book) {
        Book existing = this.getBookById(id);
        String oldTitle = existing.getTitle();
        int oldPages = existing.getPages();
        String oldIsbn = existing.getIsbn();
        if (Objects.nonNull(book)) {
            if (StringUtils.isNotEmpty(book.getTitle()) && !StringUtils.equals(book.getTitle(), oldTitle)) {
                existing.setTitle(book.getTitle());
            }
            if (StringUtils.isNotEmpty(book.getIsbn()) && !StringUtils.equals(book.getIsbn(), oldIsbn)) {
                existing.setIsbn(book.getIsbn());
            }
            if (book.getPages() != 0 && book.getPages() != oldPages) {
                existing.setPages(book.getPages());
            }
            return bookRepository.saveAndFlush(existing);
        }
        logger.error(String.format(BOOK_ALREADY_EXISTS, id));
        throw new BookException(String.format(BOOK_ALREADY_EXISTS, id), ErrorType.BAD_REQUEST);
    }

    public String deleteById(long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            logger.info("Book with id "+ id + " deleted successfully");
            return String.format("Book with id %s deleted successfully", id);
        }
        logger.error(String.format(BOOK_NOT_FOUND, id));
        throw new BookException(String.format(BOOK_NOT_FOUND, id), ErrorType.NOT_FOUND);
    }

    public Book create(Book book) {
        if (StringUtils.isEmpty(book.getIsbn()) || StringUtils.isEmpty(book.getTitle())) {
            logger.error("Isbn or title of the book is missing");
            throw new BookException("Book isbn and title are required", ErrorType.BAD_REQUEST);
        }
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            logger.error("Book with isbn " + book.getIsbn() + " already exists.");
            throw new BookException("Book with isbn " + book.getIsbn() + " already exists.", ErrorType.BAD_REQUEST);
        }
        if (authorRepository.existsById(book.getAuthor().getId())) {
            return bookRepository.save(book);
        }
        logger.error("The author of the book does not exists");
        throw new BookException("The author of the book not found", ErrorType.BAD_REQUEST);
    }

    public List<Book> search(String isbn, String title, int pages, String authorName, String nationality) {
        Optional<Book> optionalByTitle = bookRepository.findByTitle(title);
        List<Book> booksByPages = bookRepository.findByPages(pages);
        Optional<Book> optionalByIsbn = bookRepository.findByIsbn(isbn);
        optionalByTitle.ifPresent(booksByPages::add);
        optionalByIsbn.ifPresent(booksByPages::add);
        return booksByPages;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

}
