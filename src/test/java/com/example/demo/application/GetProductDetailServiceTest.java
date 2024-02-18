package com.example.demo.application;

import com.example.demo.Fixtures;
import com.example.demo.dto.AdminProductDetailDto;
import com.example.demo.model.Category;
import com.example.demo.model.CategoryId;
import com.example.demo.model.Product;
import com.example.demo.model.ProductId;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetProductDetailServiceTest {
    private ProductRepository productRepository;

    private CategoryRepository categoryRepository;

    private GetProductDetailService getProductDeailService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        categoryRepository = mock(CategoryRepository.class);

        getProductDeailService = new GetProductDetailService(
                productRepository, categoryRepository);
    }

    @Test
    void getAdminProductDetailDto() {
        Product product = Fixtures.product("맨투맨");
        ProductId productId = product.id();

        CategoryId categoryId = product.categoryId();
        Category category = new Category(categoryId, "카테고리", false);

        given(productRepository.findById(productId))
                .willReturn(Optional.of(product));

        given(categoryRepository.findById(categoryId))
                .willReturn(Optional.of(category));

        AdminProductDetailDto productDto =
                getProductDeailService.getAdminProductDetailDto(productId);

        assertThat(productDto.name()).isEqualTo("맨투맨");
    }
}