package com.example.demo.model;

//import jakarta.persistence.Column;

import jakarta.persistence.Embeddable;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.io.Serializable;
//import java.time.LocalDateTime;

@Embeddable
public class ProductOptionId extends EntityId //implements Serializable
{
    public ProductOptionId() {
        super();
    }

    public ProductOptionId(String value) {
        super(value);
    }


    public static ProductOptionId generate() {
        return new ProductOptionId(newTsid());
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
//    public ProductOptionId() {
//    }
//
//    public ProductOptionId(String value) {
//        this.value = value;
//    }
//
//    @Override
//    public String toString() {
//        return value;
//    }
}
