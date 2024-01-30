package com.example.demo.model;

import jakarta.persistence.Embeddable;


/**
 * Identifier VO 클래스
 */

@Embeddable
public class CartLineItemId extends EntityId//implements Serializable
{
    public CartLineItemId() {
        super();
    }

    public CartLineItemId(String value) {
        super(value);
    }


    public static CartLineItemId generate() {
        return new CartLineItemId(newTsid());
    }
}