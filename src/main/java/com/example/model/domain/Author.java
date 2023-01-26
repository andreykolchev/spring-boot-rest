package com.example.model.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Author {

    private Long id;
    private String fullName;
    private Date dateOfBirth;
}
