package com.example.demo.model;

//import jakarta.persistence.Column;

import jakarta.persistence.Embeddable;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.io.Serializable;
//import java.time.LocalDateTime;

@Embeddable
public class ProductId extends EntityId//implements Serializable
{
    public ProductId() {
        super();
    }

    public ProductId(String value) {
        super(value);
    }


    public static ProductId generate() {
        return new ProductId(newTsid());
    }
//    @Column(name = "id")
//    private String value;
//
//    @CreationTimestamp
//    private LocalDateTime createdAt;
//
//    @UpdateTimestamp
//    private LocalDateTime updatedAt;
//
//    public ProductId() {
//    }
//
//    public ProductId(String value) {
//        this.value = value;
//    }
//
//    @Override
//    public String toString() {
//        return value;
//    }
}