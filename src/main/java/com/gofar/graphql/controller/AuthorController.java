package com.gofar.graphql.controller;

import com.gofar.graphql.model.Author;
import com.gofar.graphql.model.AuthorInput;
import com.gofar.graphql.model.Response;
import com.gofar.graphql.service.AuthorService;
import com.gofar.graphql.utils.UtilsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.Arguments;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class AuthorController {

    private AuthorService authorService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @QueryMapping
    public List<Author> authors() {
        logger.info("Get List of all authors");
        return authorService.getAll();
    }

    @QueryMapping
    public Author getAuthorById(@Argument Long id) {
        logger.debug(String.format("Get author with id %s", id));
        return authorService.getById(id);
    }

    @QueryMapping
    public List<Author> searchAuthor(@Arguments Map<String, String> argsMap) {
        logger.debug(String.format("Search authors with properties %s", argsMap));
        return authorService.search(argsMap.get("email"), argsMap.get("name"), argsMap.get("nationality"));
    }

    @MutationMapping
    public Author saveAuthor(@Argument AuthorInput authorInput) {
        logger.debug(String.format("Save author with values %s", authorInput));
        return authorService.save(UtilsService.authorInputToAuthor(authorInput));
    }

    @MutationMapping
    public Author updateAuthor(@Argument Long id, @Argument AuthorInput authorInput) {
        logger.debug(String.format("Update author with id %s by values %s", id, authorInput));
        Author update = UtilsService.authorInputToAuthor(authorInput);
        return authorService.update(id, update);
    }

    @MutationMapping
    public Response deleteAuthorById(@Argument Long id) {
        return authorService.deleteById(id);
    }
    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }
}
