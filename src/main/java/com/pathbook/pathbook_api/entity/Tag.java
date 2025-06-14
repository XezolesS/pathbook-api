package com.pathbook.pathbook_api.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags")
public class Tag {
    // region Fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "tag_name", columnDefinition = "TINYTEXT", nullable = false)
    private String name;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostTag> posts = new ArrayList<>();

    // endregion

    // region Constructors

    protected Tag() {}

    public Tag(String name) {
        this.name = name;
    }

    // endregion

    // region Getters & Setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PostTag> getPosts() {
        return posts;
    }

    // endregion
}
