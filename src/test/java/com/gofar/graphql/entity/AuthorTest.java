package com.gofar.graphql.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;


public class AuthorTest {
    private Author author = null;

    @BeforeEach
    public void setUp() {
        author = new Author();
    }

    @Test
    public void getters() {
        Assertions.assertNull(author.getEmail());
        Assertions.assertTrue(author.getBooks().isEmpty());
        Assertions.assertNull(author.getBirthDay());
        Assertions.assertNull(author.getId());
        author = new Author("sam", "American", "sam@man.com");
        Assertions.assertEquals("sam", author.getName());
        Assertions.assertEquals("American", author.getNationality());
    }

    @Test
    public void setters() {
        author.setName("Isac");
        author.setNationality("Togolese");
        author.setEmail("isac@togolese.com");
        author.setId(9L);
        author.setBooks(new HashSet<>(Collections.singleton(new Book())));
        author.setBirthDay(LocalDate.of(2024, 2, 23));
        Assertions.assertEquals("isac@togolese.com", author.getEmail());
        Assertions.assertEquals("Togolese", author.getNationality());
        Assertions.assertEquals("Isac", author.getName());
        Assertions.assertFalse(author.getBooks().isEmpty());
        Assertions.assertEquals(LocalDate.of(2024, 2, 23), author.getBirthDay());
    }

    @Test
    public void toStringTest() {
        String result = author.toString();
        Assertions.assertNotNull(result);
        String excepted = "Author{id=null, email='null', name='null', nationality='null', birthDay=null, books=[]}";
        Assertions.assertEquals(excepted, result);
        author = new Author(3L, "AltMan", "GigaC", "altman@ia.com");
        Assertions.assertNotEquals(excepted, author.toString());
        excepted = "Author{id=null, email='null', name='null', nationality='null', birthDay=null, books=[]}";
        Assertions.assertEquals(excepted, result);
    }

    @Test
    public void addBook() {
        Book book = new Book();
        Assertions.assertTrue(author.getBooks().isEmpty());
        author.getBooks().add(book);
        Assertions.assertFalse(author.getBooks().isEmpty());
        author.addBook(new Book());
        author.removeBook(book);
        Assertions.assertEquals(1, author.getBooks().size());
    }
}
