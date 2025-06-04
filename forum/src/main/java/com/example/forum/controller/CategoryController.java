package com.example.forum.controller;

import com.example.forum.dto.CategoryRequestDto;
import com.example.forum.dto.CategoryResponseDto;
import com.example.forum.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Получить список всех категорий",
            description = "Возвращает полный список категорий форума"
    )
    @ApiResponse(responseCode = "200", description = "Список категорий получен успешно",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponseDto.class)))
    @GetMapping
    public List<CategoryResponseDto> getAllCategories() {
        log.info("Запрошен список всех категорий");
        return categoryService.getAllCategories();
    }

    @Operation(
            summary = "Создать новую категорию",
            description = "Создает новую категорию. Имя категории должно быть уникальным"
    )
    @ApiResponse(responseCode = "200", description = "Категория успешно создана",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Категория с таким именем уже существует")
    @PostMapping
    public CategoryResponseDto createCategory(
            @RequestBody(
                    description = "Данные категории",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CategoryRequestDto.class))
            )
            @org.springframework.web.bind.annotation.RequestBody @Valid CategoryRequestDto categoryDto
    ) {
        log.info("Создание категории: {}", categoryDto);
        return categoryService.createCategory(categoryDto);
    }

    @Operation(
            summary = "Получить категорию по id",
            description = "Возвращает категорию по её уникальному идентификатору"
    )
    @ApiResponse(responseCode = "200", description = "Категория найдена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Категория не найдена")
    @GetMapping("/{id}")
    public CategoryResponseDto getCategory(
            @Parameter(description = "ID категории", example = "1")
            @PathVariable Long id
    ) {
        log.info("Запрошена категория с id={}", id);
        return categoryService.getCategory(id);
    }
}