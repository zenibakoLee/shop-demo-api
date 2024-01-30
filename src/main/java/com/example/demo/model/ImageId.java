package com.example.demo.model;

//import jakarta.persistence.Column;
//import jakarta.persistence.Embeddable;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Embeddable;

@Embeddable
public class ImageId extends EntityId //implements Serializable
{
    public ImageId() {
        super();
    }

    public ImageId(String value) {
        super(value);
    }


    public static ImageId generate() {
        return new ImageId(newTsid());
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
//    public ImageId() {
//    }
//
//    public ImageId(String value) {
//        this.value = value;
//    }
//
//    @Override
//    public String toString() {
//        return value;
//    }
}