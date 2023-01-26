package com.example.service.author;

import com.example.model.dto.author.AuthorRq;
import com.example.model.dto.search.SearchRq;
import com.example.model.entity.AuthorEntity;
import com.example.repository.author.AuthorRepository;
import com.example.repository.author.AuthorSpecificationBuilder;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

@SpringBootTest(classes = {AuthorServiceImpl.class, AuthorMapper.class, AuthorSpecificationBuilder.class})
class AuthorServiceImplTest {

    private final AuthorEntity entity = getTestAuthorEntity();
    private final Long AUTHOR_ID = 1L;
    @Autowired
    AuthorService service;
    @MockBean
    private AuthorRepository repository;

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
        when(repository.findById(AUTHOR_ID)).thenReturn(Optional.of(entity));
        val result = service.getById(AUTHOR_ID);
        assertEquals(result.getId(), 1);
    }

    @Test
    void add() {
        when(repository.save(any(AuthorEntity.class))).thenReturn(entity);
        val result = service.add(getTestAuthorRq());
        assertEquals(result.getId(), 1);
    }

    @Test
    void update() {
        when(repository.findById(AUTHOR_ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(AuthorEntity.class))).thenReturn(entity);
        val result = service.add(getTestAuthorRq());
        assertEquals(result.getId(), 1);
    }

    @Test
    void delete() {
        when(repository.findById(AUTHOR_ID)).thenReturn(Optional.of(entity));
        doNothing().when(repository).deleteById(AUTHOR_ID);
        service.delete(AUTHOR_ID);
    }

    @Test
    void search() {
        val page = PageRequest.of(0, 1, Sort.by("id"));
        when(repository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(Page.empty(page));
        val result = service.search(getTestSearchRq());
        assertEquals(result.getTotalPages(), 0);
    }

    private AuthorEntity getTestAuthorEntity() {
        AuthorEntity entity = new AuthorEntity();
        entity.setId(1L);
        entity.setFullName("Test");
        entity.setDateOfBirth(new Date());
        return entity;
    }

    private AuthorRq getTestAuthorRq() {
        AuthorRq authorRq = new AuthorRq();
        authorRq.setName("Test");
        authorRq.setBirthDate(new Date());
        return authorRq;
    }

    private SearchRq getTestSearchRq() {
        SearchRq searchRq = new SearchRq();
        searchRq.setPage(0);
        searchRq.setPageSize(10);
        return searchRq;
    }
}