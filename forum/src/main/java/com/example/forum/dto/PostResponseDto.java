package com.example.forum.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Пост, возвращаемый в ответе")
public class PostResponseDto {
    @Schema(description = "ID поста", example = "101")
    private Long id;

    @Schema(description = "Заголовок поста", example = "Гайд по герою Лейла")
    private String title;

    @Schema(description = "Содержимое поста", example = "Подробный гайд по Лейле ...")
    private String content;

    @Schema(description = "Автор поста", example = "KukyoUmi")
    private String author;

    @Schema(description = "Дата создания", example = "2024-06-02T21:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Дата обновления", example = "2024-06-02T21:15:00")
    private LocalDateTime updatedAt;

    @Schema(description = "Категория поста")
    private CategoryResponseDto category;

    @Schema(description = "Комментарии к посту")
    private List<CommentResponseDto> comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CategoryResponseDto getCategory() {
        return category;
    }

    public void setCategory(CategoryResponseDto category) {
        this.category = category;
    }

    public List<CommentResponseDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponseDto> comments) {
        this.comments = comments;
    }
}
