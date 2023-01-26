package com.example.model.dto.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BookRq {

    @JsonProperty("title")
    private String title;

    @JsonProperty("authors")
    private List<Long> authors;
}
