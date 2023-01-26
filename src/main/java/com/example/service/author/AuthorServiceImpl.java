package com.example.service.author;

import com.example.model.dto.author.AuthorRq;
import com.example.model.dto.author.AuthorRs;
import com.example.model.dto.search.SearchRq;
import com.example.model.entity.AuthorEntity;
import com.example.model.error.ErrorException;
import com.example.model.error.NotFoundErrorException;
import com.example.repository.author.AuthorRepository;
import com.example.repository.author.AuthorSpecificationBuilder;
import lombok.val;
import org.mapstruct.factory.Mappers;
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
public class AuthorServiceImpl implements AuthorService {

    private final AuthorMapper mapper;
    private final AuthorRepository repository;
    private final AuthorSpecificationBuilder specificationBuilder;


    public AuthorServiceImpl(
            AuthorRepository repository,
            AuthorSpecificationBuilder specificationBuilder
    ) {
        this.mapper = Mappers.getMapper(AuthorMapper.class);
        this.repository = repository;
        this.specificationBuilder = specificationBuilder;
    }

    @Override
    public List<AuthorRs> getAll() {
        val entities = (List<AuthorEntity>) repository.findAll();
        return entities.stream().map(mapper::mapEntityToDto).collect(Collectors.toList());
    }

    @Override
    public Page<AuthorRs> getAllPaginated(Pageable page) {
        val result = repository.findAll(page);
        return result.map(mapper::mapEntityToDto);
    }

    @Override
    public AuthorRs getById(Long id) {
        val entity = repository.findById(id).orElseThrow(() -> new NotFoundErrorException("Not found by ID: " + id));
        return mapper.mapEntityToDto(entity);
    }

    @Override
    public AuthorRs add(AuthorRq dto) {
        val entity = mapper.mapDtoToEntity(dto);
        val result = repository.save(entity);
        return mapper.mapEntityToDto(result);
    }

    @Override
    public AuthorRs update(Long id, AuthorRq dto) {
        AuthorEntity entity = repository.findById(id).orElseThrow(() -> new NotFoundErrorException("Not found by ID: " + id));
        mapper.updateEntityFromDto(dto, entity);
        AuthorEntity updatedEntity = repository.save(entity);
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
    public Page<AuthorRs> search(SearchRq dto) {
        List<Sort.Order> orderList = new ArrayList<>();
        Specification<AuthorEntity> specification = specificationBuilder.build(dto.getSearchItems(), orderList);
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getPageSize(), Sort.by(orderList));
        Page<AuthorEntity> result = repository.findAll(specification, pageable);
        return result.map(mapper::mapEntityToDto);
    }
}
