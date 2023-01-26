package com.example.model.domain;

import lombok.Data;

import java.util.List;

@Data
public class Book {

    private Long id;
    private String title;
    private List<Author> authors;
}
