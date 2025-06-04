package com.example.forum.service;

import com.example.forum.dto.CategoryRequestDto;
import com.example.forum.dto.CategoryResponseDto;
import com.example.forum.exception.ResourceNotFoundException;
import com.example.forum.model.Category;
import com.example.forum.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Получить все категории (возвращаем DTO!)
    public List<CategoryResponseDto> getAllCategories() {
        log.info("Получение всех категорий");
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Создать категорию (на вход - DTO, на выход - DTO)
    public CategoryResponseDto createCategory(CategoryRequestDto requestDto) {
        log.info("Создается категория с name='{}'", requestDto.getName());
        if (categoryRepository.existsByName(requestDto.getName())) {
            log.error("Попытка создать категорию с уже существующим именем: '{}'", requestDto.getName());
            throw new IllegalArgumentException("Category with name '" + requestDto.getName() + "' already exists");
        }
        Category category = new Category();
        category.setName(requestDto.getName());
        Category saved = categoryRepository.save(category);
        return toDto(saved);
    }

    // Получить одну категорию по id (на выход - DTO)
    public CategoryResponseDto getCategory(Long id) {
        log.info("Получение категории по id={}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return toDto(category);
    }

    // ==== Мапперы ====
    private CategoryResponseDto toDto(Category category) {
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }
}
