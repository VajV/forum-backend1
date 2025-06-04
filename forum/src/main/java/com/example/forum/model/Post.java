package com.example.forum.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Пост на форуме")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор поста", example = "100")
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title too long (max 100)")
    @Column(nullable = false)
    @Schema(description = "Заголовок поста", example = "Лучшие гайды по Mobile Legends")
    private String title;

    @Lob
    @NotBlank(message = "Content cannot be blank")
    @Size(max = 10000, message = "Post is too long (max 10,000 characters)")
    @Schema(description = "Содержимое поста", example = "Здесь размещен гайд по герою Лейла...")
    private String content;

    @NotBlank(message = "Author is required")
    @Size(max = 50, message = "Author name too long (max 50)")
    @Schema(description = "Автор поста", example = "KukyoUmi")
    private String author;

    @CreationTimestamp
    @Schema(description = "Дата и время создания поста", example = "2024-06-02T21:00:00")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Schema(description = "Дата и время последнего обновления", example = "2024-06-02T21:15:00")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @Schema(description = "Категория, к которой принадлежит пост")
    private Category category;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Schema(description = "Список комментариев к посту")
    private List<Comment> comments = new ArrayList<>();
}
