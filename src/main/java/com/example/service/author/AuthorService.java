package com.example.service.author;


import com.example.model.dto.author.AuthorRq;
import com.example.model.dto.author.AuthorRs;
import com.example.model.dto.search.SearchRq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorService {

    List<AuthorRs> getAll();

    Page<AuthorRs> getAllPaginated(Pageable page);

    AuthorRs getById(Long id);

    AuthorRs add(AuthorRq author);

    AuthorRs update(Long id, AuthorRq dto);

    void delete(Long id);

    Page<AuthorRs> search(SearchRq dto);
}
