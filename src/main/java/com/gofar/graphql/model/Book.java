package com.gofar.graphql.model;

public class Book {

    private int id;
    private String title;
    private int pages;
    private Author author;

    public Book() {}

    public Book(int id, String title, int pages, Author author) {
        this.id = id;
        this.title = title;
        this.pages = pages;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
