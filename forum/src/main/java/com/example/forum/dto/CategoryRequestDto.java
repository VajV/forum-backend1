package com.example.forum.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Данные для создания или обновления категории")
public class CategoryRequestDto {
    @NotBlank(message = "Category name cannot be blank")
    @Size(max = 50, message = "Category name too long (max 50)")
    @Schema(description = "Название категории", example = "Гайды")
    private String name;

    public @NotBlank(message = "Category name cannot be blank") @Size(max = 50, message = "Category name too long (max 50)") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Category name cannot be blank") @Size(max = 50, message = "Category name too long (max 50)") String name) {
        this.name = name;
    }
}
