package com.gofar.graphql.controller;

import com.gofar.graphql.model.Author;
import com.gofar.graphql.model.AuthorInput;
import com.gofar.graphql.model.Response;
import com.gofar.graphql.service.AuthorService;
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

    @QueryMapping
    public List<Author> authors() {
        return authorService.getAll();
    }

    @QueryMapping
    public Author getAuthorById(@Argument Long id) {
        return authorService.getById(id);
    }

    @QueryMapping
    public List<Author> searchAuthor(@Arguments Map<String, String> argsMap) {
        return authorService.search(argsMap.get("name"), argsMap.get("nationality"));
    }

    @MutationMapping
    public Author saveAuthor(@Argument AuthorInput authorInput) {
        return authorService.save(authorInput.toAuthor());
    }

    @MutationMapping
    public Author updateAuthor(@Argument Long id, @Argument AuthorInput authorInput) {
        Author update = authorInput.toAuthor();
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
