package com.gofar.graphql.service;

import com.gofar.graphql.exception.AuthorException;
import com.gofar.graphql.model.Author;
import com.gofar.graphql.model.Response;
import com.gofar.graphql.repository.AuthorRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthorService {

    private final static String AUTHOR_NOT_FOUND = "Author with id %s not found";
    public AuthorRepository authorRepository;

    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public Author getById(Long id) {
        if (authorRepository.existsById(id)) {
            return authorRepository.getById(id);
        }
        throw new AuthorException(String.format(AUTHOR_NOT_FOUND, id), ErrorType.NOT_FOUND);
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public Author update(Long id, Author author) {
        Author existing = authorRepository.getById(id);
        String newName = author.getName();
        String newNationality = author.getNationality();
        int newAge = author.getAge();
        if (Objects.nonNull(existing)) {
            if (StringUtils.isNotEmpty(newName) && !existing.getName().equals(newName)) {
                existing.setName(newName);
            }
            if (StringUtils.isNotEmpty(newNationality) && !existing.getNationality().equals(newNationality)) {
                existing.setNationality(newNationality);
            }
            if (author.getAge() != 0 && existing.getAge() != newAge) {
                existing.setAge(newAge);
            }
            return authorRepository.saveAndFlush(existing);
        }
        throw new AuthorException(String.format(AUTHOR_NOT_FOUND, id), ErrorType.NOT_FOUND);
    }

    public List<Author> search(String name, String nationality) {
        Optional<Author> optional = authorRepository.findByName(name);
        List<Author> authors = authorRepository.findAllByNationality(nationality);
        optional.ifPresent(authors::add);
        return authors;
    }

    public Response deleteById(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return new Response(String.format("Author with id %s deleted successfully", id));
        }
        throw new AuthorException(String.format(AUTHOR_NOT_FOUND, id), ErrorType.NOT_FOUND);
    }

    @Autowired
    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
}
