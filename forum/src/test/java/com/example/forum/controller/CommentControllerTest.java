package com.example.forum.controller;

import com.example.forum.dto.CommentResponseDto;
import com.example.forum.dto.PostResponseDto;
import com.example.forum.service.CommentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Test
    void testGetCommentsByPostId() throws Exception {
        // Собираем DTO вручную через сеттеры
        PostResponseDto postDto = new PostResponseDto();
        postDto.setId(101L);
        postDto.setTitle("Test Post");

        CommentResponseDto commentDto = new CommentResponseDto();
        commentDto.setId(501L);
        commentDto.setAuthor("KukyoUmi");
        commentDto.setContent("Спасибо за гайд!");
        commentDto.setCreatedAt(LocalDateTime.of(2024, 6, 2, 21, 10, 0));
        commentDto.setPost(postDto);

        Mockito.when(commentService.getCommentsByPost(101L))
                .thenReturn(Collections.singletonList(commentDto));

        mockMvc.perform(get("/api/comments/post/101")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(501L))
                .andExpect(jsonPath("$[0].author").value("KukyoUmi"))
                .andExpect(jsonPath("$[0].content").value("Спасибо за гайд!"))
                .andExpect(jsonPath("$[0].createdAt").value("2024-06-02T21:10:00"))
                .andExpect(jsonPath("$[0].post.id").value(101L))
                .andExpect(jsonPath("$[0].post.title").value("Test Post"));
    }
}
