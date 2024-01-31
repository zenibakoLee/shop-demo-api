package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

/**
 * 가격을 표현하기 위한 Money 객체는 Identifier로 구분되지 않고,
 * 양적 비교로 동등성을 파악할 수 있으므로 Value Object다.
 */
@Embeddable // VO 표현할때 일반적으로 사용 + equals() and hashcode()
public class Money {
    public static final Money ZERO = new Money(0L);

    @Column(name = "amount")
    private Long amount;


    public Money(Long amount) {
        this.amount = amount;
    }

    public Money() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return amount.toString();
    }

    public Long asLong() {
        return amount;
    }

    public Money times(int quantity) {
        return new Money(this.amount * quantity);
    }

    public Money plus(Money money) {
        return new Money(this.amount + money.amount);
    }
}
