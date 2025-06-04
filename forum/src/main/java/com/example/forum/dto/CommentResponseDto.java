package com.example.forum.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Комментарий, возвращаемый в ответе")
public class CommentResponseDto {
    @Schema(description = "ID комментария", example = "501")
    private Long id;

    @Schema(description = "Автор комментария", example = "KukyoUmi")
    private String author;

    @Schema(description = "Содержимое комментария", example = "Спасибо за гайд!")
    private String content;

    @Schema(description = "Дата создания", example = "2024-06-02T21:10:00")
    private LocalDateTime createdAt;

    @Schema(description = "Пост, к которому принадлежит комментарий")
    private PostResponseDto post;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public PostResponseDto getPost() {
        return post;
    }

    public void setPost(PostResponseDto post) {
        this.post = post;
    }
}
