package com.personal.gadgetstore.service;

import com.personal.gadgetstore.dto.PageableResponse;
import com.personal.gadgetstore.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);

    List<ProductDto> createProducts(List<ProductDto> productDtoList);

    ProductDto updateProduct(int productId, ProductDto productDto);

    void deleteProduct(int productId);

    ProductDto getProductById(int productId);

    PageableResponse<ProductDto> getAllProducts(int page, int size, String sortBy, String sortDir);

    PageableResponse<ProductDto> getAllLiveProducts(int page, int size, String sortBy, String sortDir);

    PageableResponse<ProductDto> searchProductsByKeyword(String keyword,
                                                         int page,
                                                         int size,
                                                         String sortBy,
                                                         String sortDir);

    void updateImageForCategory(int categoryId, String imageName);
}