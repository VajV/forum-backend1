package com.example.forum.service;

import com.example.forum.dto.CategoryRequestDto;
import com.example.forum.dto.CategoryResponseDto;
import com.example.forum.exception.ResourceNotFoundException;
import com.example.forum.model.Category;
import com.example.forum.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    void getAllCategories_shouldReturnList() {
        Category cat = new Category();
        cat.setId(1L);
        cat.setName("TestCat");
        when(categoryRepository.findAll()).thenReturn(List.of(cat));
        List<CategoryResponseDto> result = categoryService.getAllCategories();
        assertFalse(result.isEmpty());
        assertEquals("TestCat", result.get(0).getName());
        verify(categoryRepository).findAll();
    }

    @Test
    void createCategory_existingName_shouldThrowException() {
        CategoryRequestDto req = new CategoryRequestDto();
        req.setName("Test");
        when(categoryRepository.existsByName("Test")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> categoryService.createCategory(req));
        verify(categoryRepository).existsByName("Test");
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void createCategory_newName_shouldSave() {
        CategoryRequestDto req = new CategoryRequestDto();
        req.setName("Unique");
        Category entity = new Category();
        entity.setId(1L);
        entity.setName("Unique");
        when(categoryRepository.existsByName("Unique")).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(entity);

        CategoryResponseDto saved = categoryService.createCategory(req);
        assertEquals("Unique", saved.getName());
        assertEquals(1L, saved.getId());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void getCategory_found_shouldReturn() {
        Category entity = new Category();
        entity.setId(1L);
        entity.setName("TestCat");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(entity));

        CategoryResponseDto found = categoryService.getCategory(1L);
        assertEquals(1L, found.getId());
        assertEquals("TestCat", found.getName());
        verify(categoryRepository).findById(1L);
    }

    @Test
    void getCategory_notFound_shouldThrow() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategory(99L));
        verify(categoryRepository).findById(99L);
    }
}