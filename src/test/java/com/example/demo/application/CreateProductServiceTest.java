package com.example.demo.application;

import com.example.demo.model.CategoryId;
import com.example.demo.model.Image;
import com.example.demo.model.ImageId;
import com.example.demo.model.Money;
import com.example.demo.model.Product;
import com.example.demo.model.ProductOption;
import com.example.demo.model.ProductOptionId;
import com.example.demo.model.ProductOptionItem;
import com.example.demo.model.ProductOptionItemId;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateProductServiceTest {
    private ProductRepository productRepository;

    private CreateProductService createProductService;

    @BeforeEach
    void setUpMockObjects() {
        productRepository = mock(ProductRepository.class);

        createProductService = new CreateProductService(productRepository);
    }

    @Test
    void createProduct() {
        CategoryId categoryId = CategoryId.generate();
        List<Image> images = List.of(
                new Image(ImageId.generate(), "http://example.com/image.jpg")
        );
        String name = "New Product";
        Money price = new Money(100_000L);
        List<ProductOption> options = List.of(
                new ProductOption(
                        ProductOptionId.generate(),
                        "Option #1",
                        List.of(
                                new ProductOptionItem(
                                        ProductOptionItemId.generate(),
                                        "Item #1"
                                )
                        )
                )
        );
        String description = "Description";

        Product product = createProductService.createProduct(
                categoryId, images, name, price, options, description);

        assertThat(product.name()).isEqualTo(name);

        verify(productRepository).save(any());
    }
}