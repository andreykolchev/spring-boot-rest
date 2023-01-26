package com.example.model.dto.author;

import com.example.config.databinding.DateDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.Date;

@Data
public class AuthorRq {

    @JsonProperty("name")
    private String name;

    @JsonProperty("birthDate")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date birthDate;
}
