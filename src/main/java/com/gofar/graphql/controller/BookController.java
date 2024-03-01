package com.gofar.graphql.controller;

import com.gofar.graphql.exception.BookException;
import com.gofar.graphql.entity.Book;
import com.gofar.graphql.model.BookInput;
import com.gofar.graphql.model.Response;
import com.gofar.graphql.service.BookService;
import com.gofar.graphql.utils.UtilsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.ArgumentValue;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.Arguments;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class BookController {

    private BookService bookService;
    private final Logger logger = LoggerFactory.getLogger(BookController.class);


    @QueryMapping(name = "books")
    public List<Book> getAllBooks() {
        logger.info("Retrieve the list of books");
        return bookService.getAllBooks();
    }

    @QueryMapping
    public Book getBookById(ArgumentValue<Long> id) throws Exception {
        if (id.isOmitted() || !id.isPresent()) {
            logger.error("Book is is missed");
            throw new BookException("Book id is required", ErrorType.BAD_REQUEST);
        }
        logger.debug("Get book with id " + id);
        assert id.value() != null;
        return bookService.getBookById(id.value());
    }

    @QueryMapping(name = "search")
    public List<Book> searchBook(@Arguments Map<String, String> keyValues) {
        logger.debug("Search books with using data " + keyValues);
        return bookService.search(
                keyValues.get("isbn"),
                keyValues.get("title"),
                Integer.parseInt(keyValues.get("pages")),
                keyValues.get("authorName"),
                keyValues.get("nationality")
        );
    }

    @MutationMapping(name = "updateBook")
    public Book update(@Argument Long id, @Argument BookInput bookInput) throws Exception {
        logger.debug("Update book with id " + id + " with data " + bookInput);
        return bookService.updateBook(id, UtilsService.bookInputToBook(bookInput));
    }

    @MutationMapping(name = "createBook")
    public Book createBook(@Argument BookInput bookInput) {
        logger.debug("Create book with data " + bookInput);
        return bookService.create(UtilsService.bookInputToBook(bookInput));
    }

    @MutationMapping(name = "deleteBookById")
    public Response delete(@Argument Long id) {
        logger.debug("Delete book with id " + id);
        String message = bookService.deleteById(id);
        return new Response(message);
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
}
