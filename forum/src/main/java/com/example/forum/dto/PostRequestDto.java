package com.example.forum.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Данные для создания или обновления поста")
public class PostRequestDto {
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title too long (max 100)")
    @Schema(description = "Заголовок поста", example = "Гайд по герою Лейла")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    @Size(max = 10000, message = "Post is too long (max 10,000 characters)")
    @Schema(description = "Содержимое поста", example = "Подробный гайд по Лейле ...")
    private String content;

    @NotBlank(message = "Author is required")
    @Size(max = 50, message = "Author name too long (max 50)")
    @Schema(description = "Автор поста", example = "KukyoUmi")
    private String author;

    @Schema(description = "ID категории, к которой относится пост", example = "1")
    private Long categoryId;

    public @NotBlank(message = "Title cannot be blank") @Size(max = 100, message = "Title too long (max 100)") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Title cannot be blank") @Size(max = 100, message = "Title too long (max 100)") String title) {
        this.title = title;
    }

    public @NotBlank(message = "Content cannot be blank") @Size(max = 10000, message = "Post is too long (max 10,000 characters)") String getContent() {
        return content;
    }

    public void setContent(@NotBlank(message = "Content cannot be blank") @Size(max = 10000, message = "Post is too long (max 10,000 characters)") String content) {
        this.content = content;
    }

    public @NotBlank(message = "Author is required") @Size(max = 50, message = "Author name too long (max 50)") String getAuthor() {
        return author;
    }

    public void setAuthor(@NotBlank(message = "Author is required") @Size(max = 50, message = "Author name too long (max 50)") String author) {
        this.author = author;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
