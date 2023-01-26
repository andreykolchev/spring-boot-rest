package com.example.controller.api;

import com.example.model.dto.author.AuthorRq;
import com.example.model.dto.author.AuthorRs;
import com.example.model.dto.search.SearchRq;
import com.example.service.author.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    private final String URL = "/authors";
    private final String ID = "$.id";
    private final Long AUTHOR_ID = 1L;
    private final AuthorRs authorRs = getTestAuthorRs();
    private final AuthorRq authorRq = getTestAuthorRq();
    @MockBean
    private AuthorService service;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll() throws Exception {
        when(service.getAll()).thenReturn(Collections.singletonList(authorRs));
        mvc.perform(get(URL)).andExpect(status().isOk());
    }

    @Test
    void getAllPaginated() throws Exception {
        val page = PageRequest.of(0, 1, Sort.by("id"));
        when(service.getAllPaginated(page)).thenReturn(Page.empty(page));
        mvc.perform(get(URL.concat("/paginated"))).andExpect(status().isOk());
    }

    @Test
    void getById() throws Exception {
        when(service.getById(AUTHOR_ID)).thenReturn(authorRs);
        mvc.perform(get(URL.concat("/{id}"), AUTHOR_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ID).value(AUTHOR_ID));
    }

    @Test
    void add() throws Exception {
        when(service.add(any(AuthorRq.class))).thenReturn(authorRs);
        String json = objectMapper.writeValueAsString(authorRq);
        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(ID).value(AUTHOR_ID));
    }

    @Test
    void update() throws Exception {
        when(service.update(any(Long.class), any(AuthorRq.class))).thenReturn(authorRs);
        String json = objectMapper.writeValueAsString(authorRq);
        mvc.perform(put(URL.concat("/{id}"), AUTHOR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ID).value(AUTHOR_ID));
    }

    @Test
    void deleteTest() throws Exception {
        doNothing().when(service).delete(AUTHOR_ID);
        mvc.perform(delete(URL.concat("/{id}"), AUTHOR_ID))
                .andExpect(status().isOk());
    }

    @Test
    void search() throws Exception {
        val page = PageRequest.of(0, 1, Sort.by("id"));
        when(service.search(any(SearchRq.class))).thenReturn(Page.empty(page));
        String json = objectMapper.writeValueAsString(getTestSearchRq());
        mvc.perform(post(URL.concat("/search"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    private AuthorRs getTestAuthorRs() {
        AuthorRs authorRs = new AuthorRs();
        authorRs.setId(AUTHOR_ID);
        authorRs.setName("Test");
        authorRs.setBirthDate(new Date());
        return authorRs;
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