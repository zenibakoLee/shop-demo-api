package com.example.demo.application;

import com.example.demo.dto.ProductListDto;
import com.example.demo.dto.ProductSummaryDto;
import com.example.demo.model.Category;
import com.example.demo.model.CategoryId;
import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetProductListService {
    // …(중략)…
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public GetProductListService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    public ProductListDto getProductListDto(String categoryId) {

        List<Product> products = findProducts(categoryId);

        List<ProductSummaryDto> productSummaryDtos = products.stream() // n+1 problem exist. Category @ManyToOne+ Fetch join or Kotlin+Exposed
                .map(product -> {

                    Category category = categoryRepository
                            .findById(product.categoryId())
                            .get();
                    return ProductSummaryDto.of(product, category);
                })
                .toList();

        return new ProductListDto(productSummaryDtos);
    }

    private List<Product> findProducts(String categoryId) {
        if (categoryId == null) {
            return productRepository.findAll();
        }
        CategoryId id = new CategoryId(categoryId);
        return productRepository.findAllByCategoryId(id);
    }
}