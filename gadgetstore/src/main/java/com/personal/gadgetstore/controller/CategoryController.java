package com.personal.gadgetstore.controller;

import com.personal.gadgetstore.dto.CategoryDto;
import com.personal.gadgetstore.dto.PageableResponse;
import com.personal.gadgetstore.entity.Category;
import com.personal.gadgetstore.service.CategoryService;
import com.personal.gadgetstore.service.FileService;
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
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;

    @Value("${category.image.path}")
    private String categoryImageFilePath;

    private Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Admin
    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto,@RequestParam(defaultValue = "", required = false) String admin) {
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @Admin
    @PostMapping("/batch/create")
    public ResponseEntity<List<CategoryDto>> createCategories(@Valid @RequestBody List<CategoryDto> categoryDtoList,@RequestParam(defaultValue = "", required = false) String admin) {
        List<CategoryDto> createdCategories = categoryService.createCategories(categoryDtoList);
        return new ResponseEntity<>(createdCategories, HttpStatus.CREATED);
    }

    @Admin
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable int categoryId,
                                                      @Valid @RequestBody CategoryDto categoryDto,@RequestParam(defaultValue = "", required = false) String admin) {
        CategoryDto updatedCategory = categoryService.updateCategory(categoryId, categoryDto);
        return ResponseEntity.ok(updatedCategory);
    }

    @Admin
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int categoryId,@RequestParam(defaultValue = "", required = false) String admin) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable int categoryId) {
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(categoryDto);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(@RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size,
                                                                          @RequestParam(defaultValue = "title", required = false) String sortBy,
                                                                          @RequestParam(defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<CategoryDto> categoriesPage = categoryService.getAllCategories(page, size, sortBy, sortDir);
        return ResponseEntity.ok(categoriesPage);
    }

    @Admin
    @PostMapping("/image/{categoryId}")
    public ResponseEntity<Map<String, String>> uploadCategoryImage(@RequestParam("categoryImage") MultipartFile file,
                                                                   @PathVariable int categoryId,
                                                                   @RequestParam(defaultValue = "", required = false) String admin) throws IOException {

        String imageName = fileService.uploadFile(file, categoryImageFilePath);
        Map<String, String> map = new HashMap<>();
        map.put("imageName", imageName);
        categoryService.updateImageForCategory(categoryId, imageName);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @GetMapping("/image/{categoryId}")
    public void getCategoryImage(@PathVariable int categoryId, HttpServletResponse response) throws IOException {

        CategoryDto category = categoryService.getCategoryById(categoryId);
        logger.info("Category image name: {}" + category.getCoverImage());
        InputStream inputStream = fileService.getResource(categoryImageFilePath, category.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream, response.getOutputStream());
    }
}
