package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/*
 model => jpa entity
 Aggregate Root.
 */
@Entity
@Table(name = "categories")
public class Category {

    @EmbeddedId
    private CategoryId id;

    @Column(name = "name")
    private String name;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

//    private Category() {
//    }

    public Category(CategoryId id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category() {

    }

    public CategoryId id() {
        return id;
    }

    public String name() {
        return name;
    }

}
// …(후략)…