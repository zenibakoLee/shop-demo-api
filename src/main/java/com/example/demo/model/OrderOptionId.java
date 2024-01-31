package com.example.demo.model;

import jakarta.persistence.Embeddable;

/**
 * Identifier VO 클래스
 */

@Embeddable
public class OrderOptionId extends EntityId//implements Serializable
{
    public OrderOptionId() {
        super();
    }

    public OrderOptionId(String value) {
        super(value);
    }


    public static OrderOptionId generate() {
        return new OrderOptionId(newTsid());
    }
}