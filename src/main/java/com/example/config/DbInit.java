package com.example.config;

import com.example.model.entity.AuthorEntity;
import com.example.model.entity.BookEntity;
import com.example.repository.author.AuthorRepository;
import com.example.repository.book.BookRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

@Component
public class DbInit {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final List<Long> bookIds = new ArrayList<>();
    private final List<Long> authorIds = new ArrayList<>();

    public DbInit(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @PostConstruct
    public void initData() {
        System.out.println("###############################Init Data###############################");
        AuthorEntity author1 = saveAuthor("Martin Fowler", "1951-12-01");
        AuthorEntity author2 = saveAuthor("Gregor Hohpe", "1963-09-02");
        AuthorEntity author3 = saveAuthor("Bobby Woolf", "1971-11-21");
        AuthorEntity author4 = saveAuthor("Eric Evans", "1935-02-23");
        AuthorEntity author5 = saveAuthor("Pramod J. Sadalage", "1966-07-11");

        BookEntity book1 = new BookEntity();
        book1.setTitle("Patterns of Enterprise Application Architecture");
        book1.setAuthors(Collections.singletonList(author1));
        bookRepository.save(book1);
        bookIds.add(book1.getId());

        BookEntity book2 = new BookEntity();
        book2.setTitle("Enterprise Integration Patterns");
        book2.setAuthors(asList(author2, author3));
        bookRepository.save(book2);
        bookIds.add(book2.getId());

        BookEntity book3 = new BookEntity();
        book3.setTitle("Domain-Driven Design: Tackling Complexity in the Heart of Software");
        book3.setAuthors(Collections.singletonList(author4));
        bookRepository.save(book3);
        bookIds.add(book3.getId());

        BookEntity book4 = new BookEntity();
        book4.setTitle("NoSQL Distilled: A Brief Guide to the Emerging World of Polyglot Persistence");
        book4.setAuthors(asList(author5, author1));
        bookRepository.save(book4);
        bookIds.add(book4.getId());
    }

    @SneakyThrows
    private AuthorEntity saveAuthor(String name, String dateOfBirth) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        AuthorEntity author = new AuthorEntity();
        author.setFullName(name);
        author.setDateOfBirth(simpleDateFormat.parse(dateOfBirth));
        authorRepository.save(author);
        authorIds.add(author.getId());
        return author;
    }

    @PreDestroy
    public void tearDown() {
        System.out.println("###################Tear Down Data###############################");
        bookIds.forEach(bookRepository::deleteById);
        authorIds.forEach(authorRepository::deleteById);
    }

}
