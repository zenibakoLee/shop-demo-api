package com.example.demo.model;

import jakarta.persistence.Embeddable;


/**
 * Identifier VO 클래스
 */

@Embeddable
public class CategoryId extends EntityId//implements Serializable
{
    public CategoryId() {
        super();
    }

    public CategoryId(String value) {
        super(value);
    }


    public static CategoryId generate() {
        return new CategoryId(newTsid());
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
//    public CategoryId() {
//    }
//
//    public CategoryId(String value) {
//        this.value = value;
//    }
//
//    @Override
//    public String toString() {
//        return value;
//    }
}