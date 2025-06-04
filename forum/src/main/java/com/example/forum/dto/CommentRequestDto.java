package com.example.forum.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Данные для создания комментария")
public class CommentRequestDto {
    @NotBlank(message = "Author is required")
    @Size(max = 50, message = "Author name too long (max 50)")
    @Schema(description = "Автор комментария", example = "KukyoUmi")
    private String author;

    @NotBlank(message = "Content cannot be blank")
    @Size(max = 1000, message = "Comment too long (max 1000 characters)")
    @Schema(description = "Содержимое комментария", example = "Спасибо за гайд!")
    private String content;

    @Schema(description = "ID поста, к которому относится комментарий", example = "101")
    private Long postId;

    // геттеры и сеттеры

    public @NotBlank(message = "Author is required") @Size(max = 50, message = "Author name too long (max 50)") String getAuthor() {
        return author;
    }

    public void setAuthor(@NotBlank(message = "Author is required") @Size(max = 50, message = "Author name too long (max 50)") String author) {
        this.author = author;
    }

    public @NotBlank(message = "Content cannot be blank") @Size(max = 1000, message = "Comment too long (max 1000 characters)") String getContent() {
        return content;
    }

    public void setContent(@NotBlank(message = "Content cannot be blank") @Size(max = 1000, message = "Comment too long (max 1000 characters)") String content) {
        this.content = content;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
