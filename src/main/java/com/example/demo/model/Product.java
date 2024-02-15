package com.example.demo.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @EmbeddedId
    private ProductId id;
    /*
    상품 테이블에 hidden 컬럼 추가.
    ALTER TABLE products
ADD COLUMN hidden boolean NOT NULL DEFAULT TRUE;

기존 상품은 모두 보여주게 한다.
UPDATE products SET hidden = FALSE;

상품을 추가하거나 수정할 때 문제가 없도록 “NOT NULL”을 없앤다.
ALTER TABLE images ALTER COLUMN product_id DROP NOT NULL;
ALTER TABLE product_options ALTER COLUMN product_id DROP NOT NULL;
ALTER TABLE product_option_items ALTER COLUMN product_option_id DROP NOT NULL;
     */
    @Column(name = "hidden")
    private boolean hidden;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "category_id")) // @Column override in Product
    private CategoryId categoryId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    @OrderBy("id")
    private List<Image> images = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "price"))
    private Money price;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    @OrderBy("id")
    private List<ProductOption> options = new ArrayList<>();

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    protected Product() {
    }

    public Product(ProductId id, CategoryId categoryId, List<Image> images, String name, Money price, List<ProductOption> options, String description) {
        this.id = id;
        this.categoryId = categoryId;
        this.images = images;
        this.name = name;
        this.price = price;
        this.options = options;
        this.description = description;
    }

    public CategoryId categoryId() {
        return categoryId;
    }

    public ProductId id() {
        return id;
    }

    public Image image(int index) {
        return images.get(index);
    }

    public String name() {
        return name;
    }

    public Money price() {
        return price;
    }

    public ProductOption option(int optionIndex) {
        return this.options.get(optionIndex);
    }

    public int optionSize() {
        return this.options.size();
    }

    public ProductOption optionById(ProductOptionId optionId) {
        return this.options.stream().filter((option) -> option.id().equals(optionId)).findFirst().get();
    }

    public boolean hidden() {
        return hidden;
    }
}
// …(후략)…