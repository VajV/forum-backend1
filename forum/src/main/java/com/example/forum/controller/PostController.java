package com.example.forum.controller;

import com.example.forum.dto.PostRequestDto;
import com.example.forum.dto.PostResponseDto;
import com.example.forum.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "Получить посты по категории",
            description = "Возвращает все посты, относящиеся к определённой категории"
    )
    @GetMapping("/demo")
    public String demo() {
        return postService.sayHello();
    }
    @ApiResponse(responseCode = "200", description = "Посты найдены",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponseDto.class)))
    @GetMapping("/category/{categoryId}")
    public List<PostResponseDto> getPostsByCategory(
            @Parameter(description = "ID категории", example = "1")
            @PathVariable Long categoryId
    ) {
        log.info("Запрошены посты по категории id={}", categoryId);
        return postService.getPostsByCategory(categoryId);
    }

    @Operation(
            summary = "Создать пост",
            description = "Создает новый пост в выбранной категории"
    )
    @ApiResponse(responseCode = "200", description = "Пост создан",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Некорректные данные или несуществующая категория")
    @PostMapping
    public PostResponseDto createPost(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные нового поста",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PostRequestDto.class))
            )
            @RequestBody @Valid PostRequestDto postDto
    ) {
        log.info("Создание поста: {}", postDto);
        return postService.createPost(postDto);
    }

    @Operation(
            summary = "Получить пост по id",
            description = "Возвращает пост по его идентификатору"
    )
    @ApiResponse(responseCode = "200", description = "Пост найден",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Пост не найден")
    @GetMapping("/{id}")
    public PostResponseDto getPost(
            @Parameter(description = "ID поста", example = "10")
            @PathVariable Long id
    ) {
        log.info("Запрошен пост с id={}", id);
        return postService.getPost(id);
    }
}