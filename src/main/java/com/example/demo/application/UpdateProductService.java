package com.example.demo.application;

import com.example.demo.dto.AdminUpdateProductDto;
import com.example.demo.model.Product;
import com.example.demo.model.ProductId;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateProductService {
    private final ProductRepository productRepository;

    public UpdateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void updateProduct(
            ProductId productId, AdminUpdateProductDto productDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow();

        product.update(productDto);
    }
}