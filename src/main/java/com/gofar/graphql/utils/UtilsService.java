package com.gofar.graphql.utils;

import com.gofar.graphql.model.Author;
import com.gofar.graphql.model.AuthorInput;

public class UtilsService {

    public static Author authorInputToAuthor(AuthorInput input) {
        Author author = new Author(input.getName(), input.getNationality(), input.getEmail());
        author.setBirthDay(input.getDateOfBirth());
        return author;
    }
}
