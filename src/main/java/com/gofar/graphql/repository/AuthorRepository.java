package com.gofar.graphql.repository;

import com.gofar.graphql.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query(value = "select * from author a where a.id = :id", nativeQuery = true)
    Author getById(@Param(value = "id") Long id);

    List<Author> findAllByNationality(String nationality);

    Optional<Author> findByName(String name);
}
