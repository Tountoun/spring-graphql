package com.gofar.graphql.repository;

import com.gofar.graphql.model.Author;
import com.gofar.graphql.model.Book;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class BookRepository {

    private static List<Book> books = new ArrayList<>();

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
        return null;
    }

    public Book updateBook(int id, String title) {
        Book book = this.getBookById(id);
        if (Objects.nonNull(book))
            book.setTitle(title);
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
