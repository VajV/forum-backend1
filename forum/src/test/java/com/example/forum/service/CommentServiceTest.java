package com.example.forum.service;

import com.example.forum.dto.CommentRequestDto;
import com.example.forum.dto.CommentResponseDto;
import com.example.forum.exception.ResourceNotFoundException;
import com.example.forum.model.Comment;
import com.example.forum.model.Post;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        commentRepository = mock(CommentRepository.class);
        postRepository = mock(PostRepository.class);
        commentService = new CommentService(commentRepository, postRepository);
    }

    @Test
    void getCommentsByPost_returnsComments() {
        Long postId = 100L;
        Post post = Post.builder().id(postId).title("Some post").build();

        Comment comment1 = Comment.builder()
                .id(1L).author("Author1").content("Text1")
                .createdAt(LocalDateTime.now())
                .post(post)
                .build();

        Comment comment2 = Comment.builder()
                .id(2L).author("Author2").content("Text2")
                .createdAt(LocalDateTime.now())
                .post(post)
                .build();

        when(commentRepository.findByPostId(postId)).thenReturn(Arrays.asList(comment1, comment2));

        var result = commentService.getCommentsByPost(postId);

        assertEquals(2, result.size());
        assertEquals("Author1", result.get(0).getAuthor());
        assertEquals("Text2", result.get(1).getContent());
        assertEquals(postId, result.get(0).getPost().getId());
    }

    @Test
    void getCommentsByPost_noComments_throwsException() {
        Long postId = 200L;
        when(commentRepository.findByPostId(postId)).thenReturn(Collections.emptyList());
        assertThrows(ResourceNotFoundException.class, () -> commentService.getCommentsByPost(postId));
    }

    @Test
    void createComment_success() {
        Long postId = 1L;
        Post post = Post.builder().id(postId).title("Title").build();

        CommentRequestDto req = new CommentRequestDto();
        req.setAuthor("KukyoUmi");
        req.setContent("Спасибо за гайд!");
        req.setPostId(postId);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(commentRepository.save(any(Comment.class))).thenAnswer(inv -> {
            Comment c = inv.getArgument(0);
            c.setId(10L);
            return c;
        });

        CommentResponseDto dto = commentService.createComment(req);

        assertNotNull(dto.getId());
        assertEquals("KukyoUmi", dto.getAuthor());
        assertEquals("Спасибо за гайд!", dto.getContent());
        assertEquals(postId, dto.getPost().getId());
    }

    @Test
    void createComment_noPost_throwsException() {
        Long postId = 777L;
        CommentRequestDto req = new CommentRequestDto();
        req.setAuthor("Test");
        req.setContent("No post here");
        req.setPostId(postId);

        when(postRepository.findById(postId)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> commentService.createComment(req));
    }
}
