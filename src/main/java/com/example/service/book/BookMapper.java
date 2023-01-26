package com.example.service.book;


import com.example.model.dto.author.AuthorRs;
import com.example.model.dto.book.BookRq;
import com.example.model.dto.book.BookRs;
import com.example.model.entity.AuthorEntity;
import com.example.model.entity.BookEntity;
import com.example.model.error.NotFoundErrorException;
import com.example.repository.author.AuthorRepository;
import com.example.utils.SpringUtil;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface BookMapper {

    BookRs mapEntityToDto(BookEntity entity);

    default AuthorRs authorEntityToAuthorRs(AuthorEntity authorEntity) {
        if (authorEntity == null) {
            return null;
        }
        AuthorRs authorRs = new AuthorRs();
        authorRs.setId(authorEntity.getId());
        authorRs.setBirthDate(authorEntity.getDateOfBirth());
        authorRs.setName(authorEntity.getFullName());
        return authorRs;
    }

    BookEntity mapDtoToEntity(BookRq dto);

    default AuthorEntity authorIdToAuthorEntity(Long id) {
        AuthorRepository authorRepository = SpringUtil.getBean(AuthorRepository.class);

        if (id == null) {
            return null;
        }
        return authorRepository.findById(id).orElseThrow(() -> new NotFoundErrorException("Not found by ID: " + id));
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(BookRq dto, @MappingTarget BookEntity entity);
}

