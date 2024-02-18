package com.example.demo.application;

import com.example.demo.dto.AdminProductDetailDto;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.model.ProductId;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class GetProductDetailService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public GetProductDetailService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public AdminProductDetailDto getAdminProductDetailDto(ProductId productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow();

        Category category = categoryRepository
                .findById(product.categoryId())
                .orElseThrow();

        return AdminProductDetailDto.of(product, category);
    }
}
