package com.personal.gadgetstore.service.impl;

import com.personal.gadgetstore.dto.CategoryDto;
import com.personal.gadgetstore.dto.PageableResponse;
import com.personal.gadgetstore.entity.Category;
import com.personal.gadgetstore.exception.ResourceNotFoundException;
import com.personal.gadgetstore.helper.PageableHelper;
import com.personal.gadgetstore.repository.CategoryRepository;
import com.personal.gadgetstore.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository dao;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${category.image.path}")
    private String categoryImageFilePath;

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category saved = dao.save(category);
        return modelMapper.map(saved, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(int categoryId, CategoryDto categoryDto) {
        Category category = dao.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found id:" + categoryId));
        modelMapper.map(categoryDto, category);
        category.setCategoryId(categoryId);
        Category saved = dao.save(category);
        return modelMapper.map(saved, CategoryDto.class);
    }

    @Override
    public void deleteCategory(int categoryId) {
        Category category = dao.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found id:" + categoryId));
        String imagePath = categoryImageFilePath + File.separator + category.getCoverImage();
        try {
            Files.delete(Paths.get(imagePath));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Could not delete category's cover image");
        }
        dao.delete(category);
    }

    @Override
    public CategoryDto getCategoryById(int categoryId) {
        Category category = dao.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found id:" + categoryId));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategories(int page, int size, String sortBy, String sortDir) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortDir.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        Page<Category> categoryPage = dao.findAll(PageRequest.of(page, size, direction, sortBy));
        PageableResponse<CategoryDto> response = PageableHelper.createPageableResponse(categoryPage, CategoryDto.class);
        return response;
    }

    @Override
    public List<CategoryDto> createCategories(List<CategoryDto> categoryDtoList) {
        List<Category> categories = categoryDtoList.stream()
                .map(categoryDto -> modelMapper.map(categoryDto, Category.class))
                .collect(Collectors.toList());
        List<Category> savedCategories = dao.saveAll(categories);
        return savedCategories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void updateImageForCategory(int categoryId, String imageName) {
        Category category = dao.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found categoryId:" + categoryId));
        category.setCoverImage(imageName);
        dao.save(category);
    }

}
