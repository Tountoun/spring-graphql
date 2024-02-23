package com.gofar.graphql.repository;

import com.gofar.graphql.exception.BookException;
import com.gofar.graphql.model.Author;
import com.gofar.graphql.model.Book;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class BookRepository {

    private final static List<Book> books = new ArrayList<>();

    static {
        Author yvan = new Author(5, "Yvan", "Togolese", 56);
        Author mike = new Author(3, "Mike Orig", "British", 32);
        Author musk = new Author(3, "Elon Musk", "American", 41);
        Book book = new Book(1, "Worship and Fate ", 354, yvan);
        Book book2 = new Book(4, "Title of book2 ", 210, mike);
        Book book3 = new Book(8, "Mars and Earth", 154, musk);
        Book book4 = new Book(9, "The road to Mars", 317, musk);
        books.addAll(Arrays.asList(book4, book3, book2, book));
    }

    public List<Book> getBooks() {
        return books;
    }

    public Book getBookById(int id) {
        for(var book : books) {
            if (book.getId() == id)
                return book;
        }
        throw new BookException(String.format("Book with id %s does not exists", id), ErrorType.NOT_FOUND);
    }

    public Book updateBook(int id, Book book) throws Exception {
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
            return existing;
        }
        throw new BookException(String.format("Book with id %s does not exists", id), ErrorType.NOT_FOUND);
    }

    public Book addBook(Book book) throws Exception {
        if (this.findByTitle(book.getTitle()).isPresent()) {
            throw new Exception(String.format("Book with title %s already exists", book.getTitle()));
        }
        book.setId(Integer.parseInt(RandomStringUtils.randomNumeric(4)));
        books.add(book);
        return book;
    }

    public List<Book> searchBook(String title, int pages, String authorName, String nationality) {
        Optional<Book> optional = findByTitle(title);
        List<List<Book>> bookList = Arrays.asList(
                findByPages(pages), findByAuthorName(authorName), findByAuthorNationality(nationality)
        );
        List<Book> result = new ArrayList<>();
        optional.ifPresent(result::add);
        bookList.forEach(result::addAll);

        return result;
    }

    private Optional<Book> findByTitle(String title) {
        return  books.stream().filter(book -> book.getTitle().equals(title)).findFirst();
    }

    private List<Book> findByPages(int pages) {
        return books.stream().filter(book -> book.getPages() == pages).collect(Collectors.toList());
    }

    private List<Book> findByAuthorName(String name) {
        return books.stream().filter(book -> book.getAuthor().getName().equals(name)).collect(Collectors.toList());
    }

    private List<Book> findByAuthorNationality(String nationality) {
        return books.stream().filter(book -> book.getAuthor().getNationality().equals(nationality)).collect(Collectors.toList());
    }
}
