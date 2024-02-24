package com.gofar.graphql.service;

import com.gofar.graphql.exception.BookException;
import com.gofar.graphql.model.Book;
import com.gofar.graphql.repository.BookRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {

    private BookRepository bookRepository;
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
        if (Objects.nonNull(book)) {
            if (StringUtils.isNotEmpty(book.getTitle()) && !StringUtils.equals(book.getTitle(), oldTitle)) {
                existing.setTitle(book.getTitle());
            }
            if (book.getPages() != 0 && book.getPages() != oldPages) {
                existing.setPages(book.getPages());
            }
            return bookRepository.saveAndFlush(existing);
        }
        throw new BookException(String.format(BOOK_ALREADY_EXISTS, id), ErrorType.BAD_REQUEST);
    }

    public String deleteById(long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return String.format("Book with id %s deleted successfully", id);
        }
        throw new BookException(String.format(BOOK_NOT_FOUND, id), ErrorType.NOT_FOUND);
    }

    public List<Book> search(String title, int pages, String authorName, String nationality) {
        Optional<Book> optional = bookRepository.findByTitle(title);
        List<Book> booksByPages = bookRepository.findByPages(pages);
        optional.ifPresent(booksByPages::add);
        return booksByPages;
    }
    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}
