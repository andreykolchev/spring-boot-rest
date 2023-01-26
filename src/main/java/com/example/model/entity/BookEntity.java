package com.example.model.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "book")
public class BookEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "book_author")
    private List<AuthorEntity> authors = new ArrayList<>();

}
