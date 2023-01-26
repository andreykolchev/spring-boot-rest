package com.example.model.dto.search;

import lombok.Data;

import java.util.List;

@Data
public class SearchRq {
    private Integer page = 0;
    private Integer pageSize = 20;
    private List<SearchItem> searchItems;
}
