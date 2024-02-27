package com.gofar.graphql.model;

public class BookInput {
    private String title;
    private int pages;
    private Long authorId;
    private String isbn;

    public BookInput() {}

    public BookInput(String title, int pages, Long authorId) {
        this.title = title;
        this.pages = pages;
        this.authorId = authorId;
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
