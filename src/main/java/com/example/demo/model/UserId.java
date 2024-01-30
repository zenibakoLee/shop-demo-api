package com.example.demo.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class UserId extends EntityId {
    public UserId() {
        super();
    }

    public UserId(String value) {
        super(value);
    }

    public static UserId generate() {
        return new UserId(newTsid());
    }
}
