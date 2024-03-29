package com.gofar.graphql.service;

import com.gofar.graphql.exception.AuthorException;
import com.gofar.graphql.entity.Author;
import com.gofar.graphql.model.Response;
import com.gofar.graphql.repository.AuthorRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthorService {

    private final Logger logger = LoggerFactory.getLogger(AuthorService.class);

    private final static String AUTHOR_NOT_FOUND = "Author with id %s not found";
    public AuthorRepository authorRepository;

    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public Author getById(Long id) {
        if (authorRepository.existsById(id)) {
            return authorRepository.getOneById(id);
        }
        logger.error(String.format(AUTHOR_NOT_FOUND, id));
        throw new AuthorException(String.format(AUTHOR_NOT_FOUND, id), ErrorType.NOT_FOUND);
    }

    public Author getByEmail(String email) {
        Optional<Author> optionalAuthor = authorRepository.findByEmail(email);
        return optionalAuthor.orElseGet(()-> {
            logger.error(String.format("Author with email %s not found.", email));
            throw new AuthorException(String.format("Author with email %s not found.", email), ErrorType.NOT_FOUND);
        });
    }

    public Author save(Author author) {
        if (StringUtils.isEmpty(author.getEmail())) {
            logger.error("Validation error: Email of author is required");
            throw new AuthorException("Author's email is required", ErrorType.BAD_REQUEST);
        }
        if (authorRepository.existsByEmail(author.getEmail())) {
            logger.error(String.format("Author with email %s already exists", author.getEmail()));
            throw new AuthorException(String.format("Author with email %s already exists", author.getEmail()), ErrorType.BAD_REQUEST);
        }
        return authorRepository.save(author);
    }

    public Author update(Long id, Author author) {
        Author existing = authorRepository.getOneById(id);
        String newName = author.getName();
        String newNationality = author.getNationality();
        String newEmail = author.getEmail();
        if (Objects.nonNull(existing)) {
            if (StringUtils.isNotEmpty(newName) && !existing.getName().equals(newName)) {
                existing.setName(newName);
            }
            if (StringUtils.isNotEmpty(newNationality) && !existing.getNationality().equals(newNationality)) {
                existing.setNationality(newNationality);
            }
            if (StringUtils.isNotEmpty(newEmail) && !existing.getNationality().equals(newEmail)) {
                existing.setNationality(newEmail);
            }
            if (Objects.nonNull(author.getBirthDay()) && existing.getBirthDay() != author.getBirthDay()) {
                existing.setBirthDay(author.getBirthDay());
            }
            return authorRepository.saveAndFlush(existing);
        }
        logger.error(String.format(AUTHOR_NOT_FOUND, id));
        throw new AuthorException(String.format(AUTHOR_NOT_FOUND, id), ErrorType.NOT_FOUND);
    }

    public List<Author> search(String email, String name, String nationality) {
        Optional<Author> optionalByName = authorRepository.findByName(name);
        Optional<Author> optionalByEmail = authorRepository.findByEmail(email);
        List<Author> authors = authorRepository.findAllByNationality(nationality);
        optionalByName.ifPresent(authors::add);
        optionalByEmail.ifPresent(authors::add);
        return authors;
    }

    public Response deleteById(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            logger.info(String.format("Author with id %s deleted", id));
            return new Response(String.format("Author with id %s deleted successfully", id));
        }
        logger.error(String.format(AUTHOR_NOT_FOUND, id));
        throw new AuthorException(String.format(AUTHOR_NOT_FOUND, id), ErrorType.NOT_FOUND);
    }

    @Autowired
    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
}
