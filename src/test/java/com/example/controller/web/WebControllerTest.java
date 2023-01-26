package com.example.controller.web;

import com.example.model.dto.author.AuthorRs;
import com.example.model.dto.book.BookRs;
import com.example.service.book.BookService;
import com.example.service.book.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {WebController.class, BookServiceImpl.class})
@AutoConfigureMockMvc
class WebControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    @Qualifier("booksService")
    private BookService service;

    @Test
    void helloWorldController() throws Exception {
        mvc.perform(get("/greeting/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void books() throws Exception {
        //init mock
        AuthorRs testAuthor = new AuthorRs();
        testAuthor.setName("test");
        BookRs testBook = new BookRs();
        testBook.setTitle("test");
        testBook.setAuthors(Collections.singletonList(testAuthor));
        List<BookRs> allBooks = Collections.singletonList(testBook);
        when(service.getAll()).thenReturn(allBooks);
        //perform test
        mvc.perform(get("/books/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}