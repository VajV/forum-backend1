package com.example.forum.service;

import com.example.forum.dto.CommentRequestDto;
import com.example.forum.dto.CommentResponseDto;
import com.example.forum.dto.PostResponseDto;
import com.example.forum.exception.ResourceNotFoundException;
import com.example.forum.model.Comment;
import com.example.forum.model.Post;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Scope("prototype")

public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PostService postService;

    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository,
                          PostService postService) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.postService = postService;
    }

    public List<CommentResponseDto> getCommentsByPost(Long postId) {
        log.info("Получение комментариев для поста postId={}", postId);
        List<Comment> comments = commentRepository.findByPostIdWithPost(postId);
        if (comments.isEmpty()) {
            throw new ResourceNotFoundException("No comments found for post id: " + postId);
        }
        return comments.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public CommentResponseDto createComment(CommentRequestDto requestDto) {
        log.info("Создается комментарий для postId={}, author='{}'", requestDto.getPostId(), requestDto.getAuthor());
        Long postId = requestDto.getPostId();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post does not exist"));

        Comment comment = new Comment();
        comment.setAuthor(requestDto.getAuthor());
        comment.setContent(requestDto.getContent());
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());

        Comment saved = commentRepository.save(comment);
        return toDto(saved);
    }

    // Маппер из Comment в CommentResponseDto
    private CommentResponseDto toDto(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setId(comment.getId());
        dto.setAuthor(comment.getAuthor());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());

        PostResponseDto postDto = postService.getPost(comment.getPost().getId());
        dto.setPost(postDto);
        return dto;
    }
}
