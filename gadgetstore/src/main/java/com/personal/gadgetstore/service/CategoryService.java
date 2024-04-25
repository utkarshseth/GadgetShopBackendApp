package com.personal.gadgetstore.service;

import com.personal.gadgetstore.dto.CategoryDto;
import com.personal.gadgetstore.dto.PageableResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    // Create a new category
    CategoryDto createCategory(CategoryDto categoryDto);

    // Update an existing category
    CategoryDto updateCategory(int categoryId, CategoryDto categoryDto);

    // Delete a category by its ID
    void deleteCategory(int categoryId);

    // Get a category by its ID
    CategoryDto getCategoryById(int categoryId);

    // Get all categories
    PageableResponse<CategoryDto> getAllCategories(int page, int size, String sortBy, String sortDir);

    List<CategoryDto> createCategories(List<CategoryDto> categoryDtoList);

    void updateImageForCategory(int categoryId, String imageName);

    // Search categories by a specific field (e.g., title)
//    List<CategoryDto> searchCategoriesByField(String field, String value);

}
