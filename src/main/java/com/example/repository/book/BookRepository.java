package com.example.repository.book;

import com.example.model.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends PagingAndSortingRepository<BookEntity, Long>, JpaSpecificationExecutor<BookEntity> {

    Page<BookEntity> findAll(Specification<BookEntity> specification, Pageable pageable);
}
