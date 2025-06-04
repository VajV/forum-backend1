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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<CommentResponseDto> getCommentsByPost(Long postId) {
        log.info("Получение комментариев для поста postId={}", postId);
        List<Comment> comments = commentRepository.findByPostId(postId);
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
        // Можно возвращать краткий пост или просто его id, если не хочешь полный PostResponseDto:
        PostResponseDto postDto = new PostResponseDto();
        postDto.setId(comment.getPost().getId());
        postDto.setTitle(comment.getPost().getTitle());
        dto.setPost(postDto);
        return dto;
    }
}
