package com.example.demo.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

import java.util.Objects;

@Embeddable // VO
public class CartLineItemOption {
    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "product_option_id")
    )
    private ProductOptionId productOptionId;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "product_option_item_id")
    )
    private ProductOptionItemId productOptionItemId;


    public CartLineItemOption(ProductOptionId optionId,
                              ProductOptionItemId optionItemId) {
        this.productOptionId = optionId;
        this.productOptionItemId = optionItemId;
    }

    protected CartLineItemOption() {

    }

    public ProductOptionId optionId() {
        return productOptionId;
    }

    public ProductOptionItemId optionItemId() {
        return productOptionItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartLineItemOption other = (CartLineItemOption) o;
        return Objects.equals(productOptionId, other.productOptionId) &&
                Objects.equals(productOptionItemId, other.productOptionItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productOptionId, productOptionItemId);
    }
}
