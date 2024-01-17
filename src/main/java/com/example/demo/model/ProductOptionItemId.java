package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
public class ProductOptionItemId implements Serializable {
    @Column(name = "id")
    private String value;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public ProductOptionItemId() {
    }

    public ProductOptionItemId(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
