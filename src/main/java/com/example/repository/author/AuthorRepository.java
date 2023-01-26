package com.example.repository.author;

import com.example.model.entity.AuthorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends PagingAndSortingRepository<AuthorEntity, Long>, JpaSpecificationExecutor<AuthorEntity> {

    Page<AuthorEntity> findAll(Specification<AuthorEntity> specification, Pageable pageable);
}
