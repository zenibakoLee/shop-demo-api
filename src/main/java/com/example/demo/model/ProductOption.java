package com.example.demo.model;

import com.example.demo.dto.AdminUpdateProductDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@Table(name = "product_options")
public class ProductOption {
    @EmbeddedId
    private ProductOptionId id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_option_id")
    @OrderBy("id")
    private List<ProductOptionItem> items = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public ProductOption() {
    }

    public ProductOption(ProductOptionId id, String name, List<ProductOptionItem> items) {
        this.id = id;
        this.name = name;
        this.items = items;
    }

    public ProductOptionId id() {
        return id;
    }

    public ProductOptionItem item(int optionItemIndex) {
        return this.items.get(optionItemIndex);
    }

    public String name() {
        return this.name;
    }

    public ProductOptionItem itemById(ProductOptionItemId itemId) {
        return this.items.stream().filter(productOptionItem -> productOptionItem.id().equals(itemId)).findFirst().get();
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void updateItems(List<AdminUpdateProductDto.OptionItemDto> items) {
        this.items.removeIf(item -> {
            String itemId = item.id().toString();
            return items.stream().noneMatch(i -> itemId.equals(i.id()));
        });

        items.forEach(item -> {
            if (item.id() == null) {
                this.items.add(new ProductOptionItem(
                        ProductOptionItemId.generate(),
                        item.name()
                ));
                return;
            }
            this.items.stream()
                    .filter(i -> i.id().toString().equals(item.id()))
                    .forEach(i -> i.changeName(item.name()));
        });
    }

    public List<ProductOptionItem> items() {
        return items;
    }
}