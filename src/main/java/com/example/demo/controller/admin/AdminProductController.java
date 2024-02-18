package com.example.demo.controller.admin;

import com.example.demo.annotation.AdminRequired;
import com.example.demo.application.CreateProductService;
import com.example.demo.application.GetProductDetailService;
import com.example.demo.application.GetProductListService;
import com.example.demo.application.UpdateProductService;
import com.example.demo.dto.AdminCreateProductDto;
import com.example.demo.dto.AdminProductDetailDto;
import com.example.demo.dto.AdminProductListDto;
import com.example.demo.dto.AdminUpdateProductDto;
import com.example.demo.model.CategoryId;
import com.example.demo.model.Image;
import com.example.demo.model.ImageId;
import com.example.demo.model.Money;
import com.example.demo.model.ProductId;
import com.example.demo.model.ProductOption;
import com.example.demo.model.ProductOptionId;
import com.example.demo.model.ProductOptionItem;
import com.example.demo.model.ProductOptionItemId;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AdminRequired
@RestController
@RequestMapping("/admin/products")
public class AdminProductController {
    private final GetProductListService getProductListService;
    private final GetProductDetailService getProductDetailService;
    private final CreateProductService createProductService;
    private final UpdateProductService updateProductService;

    public AdminProductController(GetProductListService getProductListService, GetProductDetailService getProductDetailService, CreateProductService createProductService, UpdateProductService updateProductService) {
        this.getProductListService = getProductListService;
        this.getProductDetailService = getProductDetailService;
        this.createProductService = createProductService;
        this.updateProductService = updateProductService;
    }

    @GetMapping
    public AdminProductListDto list() {
        return getProductListService.getAdminProductListDto();
    }

    @GetMapping("/{id}")
    public AdminProductDetailDto detail(@PathVariable String id) {
        ProductId productId = new ProductId(id);
        return getProductDetailService.getAdminProductDetailDto(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@Valid @RequestBody AdminCreateProductDto productDto) {
        createProductService.createProduct(
                new CategoryId(productDto.categoryId()),
                mapToImages(productDto.images()),
                productDto.name(),
                new Money(productDto.price()),
                mapToProductOptions(productDto.options()),
                productDto.description()
        );
        return "Created";
    }

    private List<Image> mapToImages(
            List<AdminCreateProductDto.ImageDto> imageDtos) {
        return imageDtos.stream()
                .map(image -> new Image(ImageId.generate(), image.url()))
                .toList();
    }

    private List<ProductOption> mapToProductOptions(
            List<AdminCreateProductDto.OptionDto> optionDtos) {
        return optionDtos.stream()
                .map(option -> new ProductOption(
                        ProductOptionId.generate(),
                        option.name(),
                        mapToProductOptionItems(option.items())
                ))
                .toList();
    }

    private List<ProductOptionItem> mapToProductOptionItems(
            List<AdminCreateProductDto.OptionItemDto> optionItemDtos) {
        return optionItemDtos.stream()
                .map(optionItem -> new ProductOptionItem(
                        ProductOptionItemId.generate(),
                        optionItem.name()
                ))
                .toList();
    }

    /*
    기존 Entity를 찾아서 고치는 로직이 많아서, 여기선 DTO를 Domain Layer까지 전달하기로 함.

    만약 JSON Schema가 Application Layer로 전파되는 걸 막고 싶다면, Application Layer와 Domain Layer를 위한 DTO를 따로 만들어서 변환해서 사용하면 된다.
     */
    @PatchMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @RequestBody AdminUpdateProductDto productDto) {
        updateProductService.updateProduct(new ProductId(id), productDto);
        return "Updated";
    }
}