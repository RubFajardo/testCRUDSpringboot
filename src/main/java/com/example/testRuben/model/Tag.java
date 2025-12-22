package com.example.testRuben.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    public Tag() {}

    public Tag(String title) {
        this.title = title;
    }

    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts = new HashSet<>();

    // Getters y setters
    public String getTitle() {return title;}

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }
}
