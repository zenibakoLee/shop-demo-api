package com.example.demo.application;

import com.example.demo.model.Category;
import com.example.demo.model.CategoryId;
import com.example.demo.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetCategoryListServiceTest {
    private CategoryRepository categoryRepository;
    private GetCategoryListService getCategoryListService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);

        getCategoryListService = new GetCategoryListService(categoryRepository);
    }

    @Test
    void list() {

        Category category = new Category(new CategoryId("0BV000CAT0001"), "top");

        given(categoryRepository.findAll()).willReturn(List.of(category));
        given(categoryRepository.findAllByHiddenIsFalseOrderByIdAsc())
                .willReturn(List.of(category));

        List<Category> categories = getCategoryListService.getCategories();

        assertThat(categories).hasSize(1);
        // 여기서 그냥 verify를 해도 된다. 너무 심각하게 뭔가를 테스트하려고 하지 말 것!
    }

    @Test
    @DisplayName("getAllCategories")
    void listAll() {
        CategoryId id = new CategoryId("0BV000CAT0001");
        Category category = new Category(id, "top");

        given(categoryRepository.findAllByOrderByIdAsc())
                .willReturn(List.of(category));

        List<Category> categories = getCategoryListService.getAllCategories();

        assertThat(categories).hasSize(1);
    }
}