package com.personal.gadgetstore.controller;

import com.personal.gadgetstore.dto.CategoryDto;
import com.personal.gadgetstore.dto.PageableResponse;
import com.personal.gadgetstore.dto.ProductDto;
import com.personal.gadgetstore.entity.Product;
import com.personal.gadgetstore.service.FileService;
import com.personal.gadgetstore.service.ProductService;
import com.personal.gadgetstore.validate.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String productImageFilePath;

    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.createProduct(productDto);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @Admin
    @PostMapping("/batch")
    public ResponseEntity<List<ProductDto>> createProducts(@Valid @RequestBody List<ProductDto> productDtoList,
                                                           @RequestParam(defaultValue = "", required = false) String admin) {
        List<ProductDto> createdProducts = productService.createProducts(productDtoList);
        return new ResponseEntity<>(createdProducts, HttpStatus.CREATED);
    }

    @Admin
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable int productId,
                                                    @RequestBody ProductDto productDto,
                                                    @RequestParam(defaultValue = "", required = false) String admin) {
        ProductDto updatedProduct = productService.updateProduct(productId, productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @Admin
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int productId,
                                              @RequestParam(defaultValue = "", required = false) String admin) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable int productId) {
        ProductDto productDto = productService.getProductById(productId);
        return ResponseEntity.ok(productDto);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size,
                                                                       @RequestParam(defaultValue = "id", required = false) String sortBy,
                                                                       @RequestParam(defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<ProductDto> products = productService.getAllProducts(page, size, sortBy, sortDir);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProducts(@RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size,
                                                                           @RequestParam(defaultValue = "id", required = false) String sortBy,
                                                                           @RequestParam(defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<ProductDto> liveProducts = productService.getAllLiveProducts(page, size, sortBy, sortDir);
        return ResponseEntity.ok(liveProducts);
    }

    @GetMapping("/search")
    public ResponseEntity<PageableResponse<ProductDto>> searchProductsByKeyword(@RequestParam String keyword,
                                                                                @RequestParam(defaultValue = "0") int page,
                                                                                @RequestParam(defaultValue = "10") int size,
                                                                                @RequestParam(defaultValue = "id", required = false) String sortBy,
                                                                                @RequestParam(defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<ProductDto> products = productService.searchProductsByKeyword(keyword,
                page,
                size,
                sortBy,
                sortDir);
        return ResponseEntity.ok(products);
    }

    @Admin
    @PostMapping("/image/{productId}")
    public ResponseEntity<Map<String, String>> uploadProductImage(@RequestParam(value = "productImage") MultipartFile image,
                                                                  @PathVariable int productId,
                                                                  @RequestParam(defaultValue = "", required = false) String admin) throws IOException {
        String imageName = fileService.uploadFile(image, productImageFilePath);
        Map<String, String> map = new HashMap<>();
        map.put("imageName", imageName);
        productService.updateImageForCategory(productId, imageName);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @GetMapping("/image/{productId}")
    public void getCategoryImage(@PathVariable int productId, HttpServletResponse response) throws IOException {

        ProductDto productDto = productService.getProductById(productId);
        logger.info("Product image name: {}" + productDto.getProductImage());
        InputStream inputStream = fileService.getResource(productImageFilePath, productDto.getProductImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream, response.getOutputStream());
    }
}
