package com.gofar.graphql.controller;

import com.gofar.graphql.model.Book;
import com.gofar.graphql.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.ArgumentValue;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookController {

    private BookRepository bookRepository;


    @QueryMapping(name = "books")
    public List<Book> getAllBooks() {
        return bookRepository.getBooks();
    }

    @QueryMapping
    public Book getBookById(ArgumentValue<Integer> id) throws Exception {
        if (id.isOmitted() || !id.isPresent()) {
            throw new Exception("Book id is required");
        }

        assert id.value() != null;
        return bookRepository.getBookById(id.value());
    }

    @QueryMapping(name = "search")
    public List<Book> searchBook(@Argument String title, @Argument Integer pages, @Argument String authorName, @Argument String nationality) {
        return bookRepository.searchBook(title, pages, authorName, nationality);
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}
