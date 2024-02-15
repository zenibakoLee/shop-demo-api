package com.example.demo.controller.admin;

import com.example.demo.annotation.AdminRequired;
import com.example.demo.application.GetProductListService;
import com.example.demo.dto.AdminProductListDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AdminRequired
@RestController
@RequestMapping("/admin/products")
public class AdminProductController {
    private final GetProductListService getProductListService;

    public AdminProductController(GetProductListService getProductListService) {
        this.getProductListService = getProductListService;
    }

    @GetMapping
    public AdminProductListDto list() {
        return getProductListService.getAdminProductListDto();
    }
}