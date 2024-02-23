package com.gofar.graphql.controller;

import com.gofar.graphql.exception.BookException;
import com.gofar.graphql.model.Book;
import com.gofar.graphql.model.BookInput;
import com.gofar.graphql.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.ArgumentValue;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.execution.ErrorType;
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
            throw new BookException("Book id is required", ErrorType.BAD_REQUEST);
        }

        assert id.value() != null;
        return bookRepository.getBookById(id.value());
    }

    @QueryMapping(name = "search")
    public List<Book> searchBook(@Argument String title, @Argument Integer pages, @Argument String authorName, @Argument String nationality) {
        return bookRepository.searchBook(title, pages, authorName, nationality);
    }

    @MutationMapping(name = "updateBook")
    public Book update(@Argument int id, @Argument BookInput bookInput) throws Exception {
        return bookRepository.updateBook(id, bookInput.toBook());
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}
