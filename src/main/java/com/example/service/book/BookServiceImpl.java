package com.example.service.book;

import com.example.model.dto.book.BookRq;
import com.example.model.dto.book.BookRs;
import com.example.model.dto.search.SearchRq;
import com.example.model.entity.BookEntity;
import com.example.model.error.ErrorException;
import com.example.model.error.NotFoundErrorException;
import com.example.repository.book.BookRepository;
import com.example.repository.book.BookSpecificationBuilder;
import lombok.val;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Qualifier("booksService")
public class BookServiceImpl implements BookService {

    private final BookMapper mapper;
    private final BookRepository repository;
    private final BookSpecificationBuilder specificationBuilder;

    public BookServiceImpl(
            BookRepository repository,
            BookSpecificationBuilder specificationBuilder) {
        this.specificationBuilder = specificationBuilder;
        this.mapper = Mappers.getMapper(BookMapper.class);
        this.repository = repository;
    }

    @Override
    public List<BookRs> getAll() {
        val entities = (List<BookEntity>) repository.findAll();
        return entities.stream().map(mapper::mapEntityToDto).collect(Collectors.toList());
    }

    @Override
    public Page<BookRs> getAllPaginated(Pageable page) {
        val result = repository.findAll(page);
        return result.map(mapper::mapEntityToDto);
    }

    @Override
    public BookRs getById(Long id) {
        val entity = repository.findById(id).orElseThrow(() -> new NotFoundErrorException("Not found by ID: " + id));
        return mapper.mapEntityToDto(entity);
    }

    @Override
    public BookRs add(BookRq dto) {
        val entity = mapper.mapDtoToEntity(dto);
        val result = repository.save(entity);
        return mapper.mapEntityToDto(result);
    }

    @Override
    public BookRs update(Long id, BookRq dto) {
        BookEntity entity = repository.findById(id).orElseThrow(() -> new NotFoundErrorException("Not found by ID: " + id));
        mapper.updateEntityFromDto(dto, entity);
        BookEntity updatedEntity = repository.save(entity);
        return mapper.mapEntityToDto(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        repository.findById(id).orElseThrow(() -> new NotFoundErrorException("Not found by ID: " + id));
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new ErrorException("Could not execute statement.");
        }
    }

    @Override
    public Page<BookRs> search(SearchRq dto) {
        List<Sort.Order> orderList = new ArrayList<>();
        Specification<BookEntity> specification = specificationBuilder.build(dto.getSearchItems(), orderList);
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getPageSize(), Sort.by(orderList));
        Page<BookEntity> result = repository.findAll(specification, pageable);
        return result.map(mapper::mapEntityToDto);
    }
}
