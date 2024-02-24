package com.gofar.graphql.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nationality;
    private int age;

    @OneToMany
    private Set<Book> books = new HashSet<>();

    public Author(){}

    public Author(Long id, String name, String nationality, int age) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.age = age;
    }

    public Author(String name, String nationality, int age) {
        this.name = name;
        this.nationality = nationality;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }
}
