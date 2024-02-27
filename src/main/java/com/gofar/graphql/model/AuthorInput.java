package com.gofar.graphql.model;

import java.time.LocalDate;

public class AuthorInput {
    private String name;
    private String nationality;
    private String email;
    private LocalDate dateOfBirth;

    public AuthorInput() { }
    public AuthorInput(String email, String name, String nationality, LocalDate dateOfBirth) {
        this.email = email;
        this.name = name;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "AuthorInput{" +
                "name='" + name + '\'' +
                ", nationality='" + nationality + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                '}';
    }
}
