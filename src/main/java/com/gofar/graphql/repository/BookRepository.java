package com.gofar.graphql.repository;

import com.gofar.graphql.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    public boolean existsByTitle(String title);

    public Optional<Book> findByTitle(String title);

    public List<Book> findByPages(int pages);

    boolean existsByIsbn(String isbn);

    public Optional<Book> findByIsbn(String isbn);
}
