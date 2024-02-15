package com.example.demo.controller.admin;

import com.example.demo.application.GetProductListService;
import com.example.demo.controller.ControllerTest;
import com.example.demo.dto.AdminProductListDto;
import com.example.demo.dto.AdminProductSummaryDto;
import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.ImageDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminProductController.class)
class AdminProductControllerTest extends ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetProductListService getProductListService;

    @Test
    @DisplayName("GET /admin/products")
    void list() throws Exception {
        AdminProductSummaryDto productDto = new AdminProductSummaryDto(
                "PRODUCT-ID",
                new CategoryDto("CATEGORY-ID", "Category"),
                new ImageDto("http://example.com/01.jpg"),
                "Product",
                100_000L,
                false
        );

        given(getProductListService.getAdminProductListDto())
                .willReturn(new AdminProductListDto(List.of(productDto)));

        mockMvc.perform(get("/admin/products")
                        .header("Authorization", "Bearer " + adminAccessToken))
                .andExpect(status().isOk());
    }
}