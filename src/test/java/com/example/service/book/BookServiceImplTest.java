package com.example.service.book;

import com.example.model.dto.book.BookRq;
import com.example.model.dto.search.SearchRq;
import com.example.model.entity.AuthorEntity;
import com.example.model.entity.BookEntity;
import com.example.repository.author.AuthorRepository;
import com.example.repository.book.BookRepository;
import com.example.repository.book.BookSpecificationBuilder;
import com.example.utils.SpringUtil;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {BookServiceImpl.class, BookMapper.class, BookSpecificationBuilder.class})
class BookServiceImplTest {

    private final BookEntity entity = getTestBookEntity();
    private final Long BOOK_ID = 1L;
    @Autowired
    BookService service;
    @Autowired
    ApplicationContext applicationContext;
    @MockBean
    private BookRepository repository;
    @MockBean
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        SpringUtil.setApplicationContext(applicationContext);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(getTestAuthorEntity()));
    }

    @Test
    void getAll() {
        when(repository.findAll()).thenReturn(Collections.singletonList(entity));
        val result = service.getAll();
        assertEquals(result.get(0).getId(), 1);
    }

    @Test
    void getAllPaginated() {
        val page = PageRequest.of(0, 1, Sort.by("id"));
        when(repository.findAll(page)).thenReturn(Page.empty(page));
        val result = service.getAllPaginated(page);
        assertEquals(result.getTotalPages(), 0);
    }

    @Test
    void getById() {
        when(repository.findById(BOOK_ID)).thenReturn(Optional.of(entity));
        val result = service.getById(BOOK_ID);
        assertEquals(result.getId(), 1);
    }

    @Test
    void add() {
        when(repository.save(any(BookEntity.class))).thenReturn(entity);
        val result = service.add(getTestBookRq());
        assertEquals(result.getId(), 1);
    }

    @Test
    void update() {
        when(repository.findById(BOOK_ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(BookEntity.class))).thenReturn(entity);
        val result = service.add(getTestBookRq());
        assertEquals(result.getId(), 1);
    }

    @Test
    void delete() {
        when(repository.findById(BOOK_ID)).thenReturn(Optional.of(entity));
        doNothing().when(repository).deleteById(BOOK_ID);
        service.delete(BOOK_ID);
    }

    @Test
    void search() {
        val page = PageRequest.of(0, 1, Sort.by("id"));
        when(repository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(Page.empty(page));
        val result = service.search(getTestSearchRq());
        assertEquals(result.getTotalPages(), 0);
    }

    private BookEntity getTestBookEntity() {
        BookEntity entity = new BookEntity();
        entity.setId(1L);
        entity.setTitle("Test");
        entity.setAuthors(Collections.singletonList(getTestAuthorEntity()));
        return entity;
    }

    private AuthorEntity getTestAuthorEntity() {
        AuthorEntity entity = new AuthorEntity();
        entity.setId(1L);
        entity.setDateOfBirth(new Date());
        entity.setFullName("Test");
        return entity;
    }

    private BookRq getTestBookRq() {
        BookRq BookRq = new BookRq();
        BookRq.setTitle("Test");
        BookRq.setAuthors(Collections.singletonList(1L));
        return BookRq;
    }

    private SearchRq getTestSearchRq() {
        SearchRq searchRq = new SearchRq();
        searchRq.setPage(0);
        searchRq.setPageSize(10);
        return searchRq;
    }

}