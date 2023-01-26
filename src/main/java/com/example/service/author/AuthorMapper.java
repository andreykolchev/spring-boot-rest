package com.example.service.author;


import com.example.model.dto.author.AuthorRq;
import com.example.model.dto.author.AuthorRs;
import com.example.model.entity.AuthorEntity;
import org.mapstruct.*;

@Mapper
public interface AuthorMapper {

    @Mappings({
            @Mapping(target = "name", source = "fullName"),
            @Mapping(target = "birthDate", source = "dateOfBirth")
    })
    AuthorRs mapEntityToDto(AuthorEntity entity);

    @Mappings({
            @Mapping(target = "fullName", source = "name"),
            @Mapping(target = "dateOfBirth", source = "birthDate")
    })
    AuthorEntity mapDtoToEntity(AuthorRq dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "fullName", source = "name"),
            @Mapping(target = "dateOfBirth", source = "birthDate")
    })
    void updateEntityFromDto(AuthorRq dto, @MappingTarget AuthorEntity entity);

}

