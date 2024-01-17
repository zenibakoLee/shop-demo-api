package com.example.demo.repository;

import com.example.demo.model.CategoryId;
import com.example.demo.model.Product;
import com.example.demo.model.ProductId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, ProductId> {
    List<Product> findAll();

    List<Product> findAllByCategoryId(CategoryId categoryId);
}
