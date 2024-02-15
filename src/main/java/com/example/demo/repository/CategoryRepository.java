package com.example.demo.repository;

import com.example.demo.model.Category;
import com.example.demo.model.CategoryId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, CategoryId> {
    List<Category> findAll(); // default : Iterable<T> => List<>

    List<Category> findAllByHiddenIsFalseOrderByIdAsc();

    List<Category> findAllByOrderByIdAsc();
}
