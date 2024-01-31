package com.example.demo.model;

import jakarta.persistence.Embeddable;

/**
 * Identifier VO 클래스
 */

@Embeddable
public class OrderId extends EntityId//implements Serializable
{
    public OrderId() {
        super();
    }

    public OrderId(String value) {
        super(value);
    }


    public static OrderId generate() {
        return new OrderId(newTsid());
    }
}