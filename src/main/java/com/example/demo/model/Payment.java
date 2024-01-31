package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Payment {
    @Column(name = "payment_merchant_id")
    private String merchantId;

    @Column(name = "payment_transaction_id")
    private String transactionId;

    protected Payment() {
    }

    public Payment(String merchantId, String transactionId) {
        this.merchantId = merchantId;
        this.transactionId = transactionId;
    }

    public String transactionId() {
        return transactionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Payment other = (Payment) o;
        return Objects.equals(merchantId, other.merchantId) &&
                Objects.equals(transactionId, other.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(merchantId, transactionId);
    }
}