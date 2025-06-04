package com.example.forum.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Комментарий к посту")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор комментария", example = "501")
    private Long id;

    @NotBlank(message = "Author is required")
    @Size(max = 50, message = "Author name too long (max 50)")
    @Schema(description = "Автор комментария", example = "KukyoUmi")
    private String author;

    @Lob
    @NotBlank(message = "Content cannot be blank")
    @Size(max = 1000, message = "Comment too long (max 1000 characters)")
    @Schema(description = "Содержимое комментария", example = "Спасибо за подробный гайд!")
    private String content;

    @CreationTimestamp
    @Schema(description = "Дата и время создания комментария", example = "2024-06-02T21:10:00")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    @Schema(description = "Пост, к которому оставлен комментарий")
    private Post post;
}

