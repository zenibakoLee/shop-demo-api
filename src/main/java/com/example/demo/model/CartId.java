package com.example.demo.model;

import jakarta.persistence.Embeddable;

/**
 * Identifier VO 클래스
 */

@Embeddable
public class CartId extends EntityId//implements Serializable
{
    public CartId() {
        super();
    }

    public CartId(String value) {
        super(value);
    }


    public static CartId generate() {
        return new CartId(newTsid());
    }
}
