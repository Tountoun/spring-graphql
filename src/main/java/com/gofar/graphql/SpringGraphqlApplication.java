package com.gofar.graphql;

import com.gofar.graphql.model.Author;
import com.gofar.graphql.model.Book;
import com.gofar.graphql.repository.AuthorRepository;
import com.gofar.graphql.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SpringGraphqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringGraphqlApplication.class, args);
	}

	@Bean
	ApplicationRunner runner() {

		return args -> {
			Author yvan = new Author("Yvan", "Togolese", 56);
			Author mike = new Author("Mike Orig", "British", 32);
			Author musk = new Author("Elon Musk", "American", 41);
			Book book = new Book("Worship and Fate ", 354, yvan);
			Book book2 = new Book("Title of book2 ", 210, mike);
			Book book3 = new Book("Mars and Earth", 154, musk);
			Book book4 = new Book("The road to Mars", 317, musk);
			authorRepository.saveAll(List.of(yvan, mike, musk));
			bookRepository.saveAll(List.of(book, book2, book3, book4));
		};
	}

	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private BookRepository bookRepository;
}
