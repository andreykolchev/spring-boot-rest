package com.example.service.book;


import com.example.model.dto.book.BookRq;
import com.example.model.dto.book.BookRs;
import com.example.model.dto.search.SearchRq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    List<BookRs> getAll();

    Page<BookRs> getAllPaginated(Pageable page);

    BookRs getById(Long id);

    BookRs add(BookRq author);

    BookRs update(Long id, BookRq dto);

    void delete(Long id);

    Page<BookRs> search(SearchRq dto);
}
