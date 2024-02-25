package com.gofar.graphql.controller;

import com.gofar.graphql.exception.BookException;
import com.gofar.graphql.model.Book;
import com.gofar.graphql.model.BookInput;
import com.gofar.graphql.model.Response;
import com.gofar.graphql.service.BookService;
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

    private BookService bookService;


    @QueryMapping(name = "books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @QueryMapping
    public Book getBookById(ArgumentValue<Integer> id) throws Exception {
        if (id.isOmitted() || !id.isPresent()) {
            throw new BookException("Book id is required", ErrorType.BAD_REQUEST);
        }
        assert id.value() != null;
        return bookService.getBookById(id.value().longValue());
    }

    @QueryMapping(name = "search")
    public List<Book> searchBook(@Argument String title, @Argument Integer pages, @Argument String authorName, @Argument String nationality) {
        return bookService.search(title, pages, authorName, nationality);
    }

    @MutationMapping(name = "updateBook")
    public Book update(@Argument int id, @Argument BookInput bookInput) throws Exception {
        return bookService.updateBook((long) id, bookInput.toBook());
    }

    @MutationMapping(name = "createBook")
    public Book createBook(@Argument BookInput bookInput) {
        return bookService.create(bookInput.toBook());
    }

    @MutationMapping(name = "deleteBookById")
    public Response delete(@Argument int id) {
        String message = bookService.deleteById((long)id);
        return new Response(message);
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
}
