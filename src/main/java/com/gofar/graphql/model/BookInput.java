package com.gofar.graphql.model;

public class BookInput {
    private String title;
    private int pages;
    public BookInput() {}

    public BookInput(String title, int pages) {
        this.title = title;
        this.pages = pages;
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

    public Book toBook() {
        Book book = new Book();
        book.setTitle(title);
        book.setPages(pages);
        return book;
    }
}
