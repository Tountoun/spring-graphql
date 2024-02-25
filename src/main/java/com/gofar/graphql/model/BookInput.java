package com.gofar.graphql.model;

public class BookInput {
    private String title;
    private int pages;
    private int authorId;

    public BookInput() {}

    public BookInput(String title, int pages, int authorId) {
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

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public Book toBook() {
        Author author = new Author();
        Book book = new Book();
        book.setTitle(title);
        book.setPages(pages);
        author.setId((long) authorId);
        book.setAuthor(author);
        return book;
    }
}
