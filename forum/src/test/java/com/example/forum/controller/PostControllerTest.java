package com.example.forum.controller;

import com.example.forum.dto.PostRequestDto;
import com.example.forum.dto.PostResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetPostById_NotFound() throws Exception {
        mockMvc.perform(get("/api/posts/{id}", 9999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreatePost_BadRequest() throws Exception {
        // Некорректный запрос (например, пустое поле)
        PostRequestDto postRequestDto = new PostRequestDto();
        postRequestDto.setTitle(""); // Пустой заголовок
        postRequestDto.setContent("Контент");
        postRequestDto.setCategoryId(1L);

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequestDto)))
                .andExpect(status().isBadRequest());
    }

    // Можно добавить позитивный тест, если есть заполненная тестовая БД и миграции
}
