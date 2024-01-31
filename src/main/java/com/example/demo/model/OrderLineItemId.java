package com.example.demo.model;

import jakarta.persistence.Embeddable;


/**
 * Identifier VO 클래스
 */

@Embeddable
public class OrderLineItemId extends EntityId//implements Serializable
{
    public OrderLineItemId() {
        super();
    }

    public OrderLineItemId(String value) {
        super(value);
    }


    public static OrderLineItemId generate() {
        return new OrderLineItemId(newTsid());
    }
}