package com.example.model.dto.search;


import lombok.Data;

@Data
public class SearchItem {
    private String key;
    private String value;
    private String sort = "ASC";
}
