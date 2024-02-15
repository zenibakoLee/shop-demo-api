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

ALTER TABLE categories
ADD COLUMN hidden boolean NOT NULL DEFAULT TRUE;
UPDATE categories SET hidden = FALSE;
 */
@Entity
@Table(name = "categories")
public class Category {

    @EmbeddedId
    private CategoryId id;

    @Column(name = "name")
    private String name;

    @Column(name = "hidden")
    private boolean hidden;

    public Category(CategoryId id, String name, boolean hidden) {
        this.id = id;
        this.name = name;
        this.hidden = hidden;
    }

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

//    private Category() {
//    }

    public Category(CategoryId id, String name) {
        this(id, name, false);
    }

    public Category() {

    }

    public CategoryId id() {
        return id;
    }

    public String name() {
        return name;
    }

    public boolean hidden() {
        return hidden;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void show() {
        this.hidden = false;
    }

    public void hide() {
        this.hidden = true;
    }
}
// …(후략)…