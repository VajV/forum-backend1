package com.example.forum.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Категория, возвращаемая в ответе")
public class CategoryResponseDto {
    @Schema(description = "ID категории", example = "1")
    private Long id;

    @Schema(description = "Название категории", example = "Гайды")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
