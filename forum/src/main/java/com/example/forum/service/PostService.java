package com.example.forum.service;

import com.example.forum.annotation.MyAbstract;
import com.example.forum.dto.PostRequestDto;
import com.example.forum.dto.PostResponseDto;
import com.example.forum.dto.CategoryResponseDto;
import com.example.forum.dto.CommentResponseDto;
import com.example.forum.exception.ResourceNotFoundException;
import com.example.forum.model.Category;
import com.example.forum.model.Comment;
import com.example.forum.model.Post;
import com.example.forum.repository.CategoryRepository;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.PostRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostService{
    Map<String, Integer> map = new HashMap<>();
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    public PostService(PostRepository postRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    @MyAbstract
    public String sayHello() {
        System.out.println("Метод sayHello выполняется");
        return "Hello from service!";
    }
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostsByCategory(Long categoryId) {
        List<Post> posts = postRepository.findByCategoryId(categoryId);
        for (Post post : posts) {
            Hibernate.initialize(post.getComments());
        }
        return posts.stream().map(this::toResponseDto).collect(Collectors.toList());
    }

    // Создать пост (DTO)
    public PostResponseDto createPost(PostRequestDto postDto) {
        log.info("Создается пост с title='{}'", postDto.getTitle());

        Long categoryId = postDto.getCategoryId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category with id " + categoryId + " does not exist"));

        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .author(postDto.getAuthor())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .category(category)
                .build();

        return toResponseDto(postRepository.save(post));
    }

    // Получить пост по id (DTO)
    public PostResponseDto getPost(Long id) {
        log.info("Получение поста по id={}", id);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        return toResponseDto(post);
    }

    // -------------------- MAP STRUCTURE ------------------------

    private PostResponseDto toResponseDto(Post post) {
        PostResponseDto dto = new PostResponseDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setAuthor(post.getAuthor());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());

        // Category
        CategoryResponseDto catDto = new CategoryResponseDto();
        catDto.setId(post.getCategory().getId());
        catDto.setName(post.getCategory().getName());
        dto.setCategory(catDto);

        if (post.getComments() != null) {
            List<CommentResponseDto> comments = post.getComments().stream()
                    .map(this::toCommentDto)
                    .collect(Collectors.toList());
            dto.setComments(comments);
        }
        return dto;
    }

    private CommentResponseDto toCommentDto(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setId(comment.getId());
        dto.setAuthor(comment.getAuthor());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        // dto.setPost(...) — если хочешь возвращать данные о посте внутри комментария
        return dto;
    }
}
