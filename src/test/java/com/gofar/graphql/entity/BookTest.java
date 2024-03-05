package com.gofar.graphql.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookTest {

    private Book book = null;

    @BeforeEach
    public void setUp() {
        book = new Book();
    }

    @Test
    public void getters() {
        Assertions.assertNull(book.getIsbn());
        Assertions.assertNull(book.getTitle());
        Assertions.assertNull(book.getAuthor());
        Assertions.assertEquals(book.getPages(), 0);
        book = new Book(4L, "Days of failure", 422, new Author());
        Assertions.assertEquals(4L, book.getId());
        Assertions.assertEquals("Days of failure", book.getTitle());
        Assertions.assertNotNull(book.getAuthor());
    }

    @Test
    public void setters() {
        book.setIsbn("978-0-7432-1145-8");
        book.setPages(232);
        book.setTitle("Fight for success");
        book.setAuthor(new Author("Tig", "Gof", "tig@gofar.com"));
        book.setId(12L);
        Assertions.assertEquals(232, book.getPages());
        Assertions.assertEquals("978-0-7432-1145-8", book.getIsbn());
        Assertions.assertEquals("Fight for success", book.getTitle());
        Assertions.assertNotNull(book.getAuthor());
        Assertions.assertEquals("Tig", book.getAuthor().getName());
        Assertions.assertEquals("tig@gofar.com", book.getAuthor().getEmail());
    }

    @Test
    public void toStringTest() {
        Assertions.assertNotNull(book.toString());
        String excepted = "Book{id=null, isbn='null', title='null', pages=0, author=null}";
        Assertions.assertEquals(excepted, book.toString());
        book = new Book("Seas of Hearth", "978-0-7432-1145-8", 133, new Author());
        excepted = "Book{id=null, isbn='978-0-7432-1145-8', title='Seas of Hearth', pages=133, author=Author{id=null, email='null', name='null', nationality='null', birthDay=null, books=[]}}";
        Assertions.assertEquals(excepted, book.toString());
    }
}
