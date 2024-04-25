package com.personal.gadgetstore.service.impl;

import com.personal.gadgetstore.dto.PageableResponse;
import com.personal.gadgetstore.dto.ProductDto;
import com.personal.gadgetstore.entity.Category;
import com.personal.gadgetstore.entity.Product;
import com.personal.gadgetstore.exception.ResourceNotFoundException;
import com.personal.gadgetstore.helper.PageableHelper;
import com.personal.gadgetstore.repository.ProductRepository;
import com.personal.gadgetstore.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public List<ProductDto> createProducts(List<ProductDto> productDtoList) {
        List<Product> products = productDtoList.stream()
                .map(productDto -> modelMapper.map(productDto, Product.class))
                .collect(Collectors.toList());
        List<Product> savedProducts = productRepository.saveAll(products);
        return savedProducts.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProduct(int productId, ProductDto productDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        modelMapper.map(productDto, product);
        product.setId(productId);
        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void deleteProduct(int productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }
        productRepository.deleteById(productId);
    }

    @Override
    public ProductDto getProductById(int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(int page, int size, String sortBy, String sortDir) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortDir.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        Page<Product> productPage = productRepository.findAll(PageRequest.of(page, size, direction, sortBy));
        PageableResponse<ProductDto> response = PageableHelper.createPageableResponse(productPage, ProductDto.class);
        return response;

    }

    @Override
    public PageableResponse<ProductDto> getAllLiveProducts(int page, int size, String sortBy, String sortDir) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortDir.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        Page<Product> productPage = productRepository.findByLiveTrue(PageRequest.of(page, size, direction, sortBy));
        PageableResponse<ProductDto> response = PageableHelper.createPageableResponse(productPage, ProductDto.class);
        return response;
    }

    @Override
    public PageableResponse<ProductDto> searchProductsByKeyword(String keyword,
                                                                int page,
                                                                int size,
                                                                String sortBy,
                                                                String sortDir) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortDir.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        Page<Product> productPage = productRepository.findByTitleContainingIgnoreCase(keyword,
                PageRequest.of(page, size, direction, sortBy));
        PageableResponse<ProductDto> response = PageableHelper.createPageableResponse(productPage, ProductDto.class);
        return response;
    }

    @Override
    public void updateImageForCategory(int productId, String imageName) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        product.setProductImage(imageName);
        productRepository.save(product);
    }

}
