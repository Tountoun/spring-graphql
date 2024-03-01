package com.gofar.graphql.utils;

import com.gofar.graphql.entity.Author;
import com.gofar.graphql.model.AuthorInput;
import com.gofar.graphql.entity.Book;
import com.gofar.graphql.model.BookInput;

public class UtilsService {

    public static Author authorInputToAuthor(AuthorInput input) {
        Author author = new Author(input.getName(), input.getNationality(), input.getEmail());
        author.setBirthDay(input.getDateOfBirth());
        return author;
    }

    public static Book bookInputToBook(BookInput bookInput) {
        Author author = new Author();
        Book book = new Book();
        book.setTitle(bookInput.getTitle());
        book.setPages(bookInput.getPages());
        book.setIsbn(bookInput.getIsbn());
        author.setId(bookInput.getAuthorId());
        book.setAuthor(author);
        return book;
    }
}
