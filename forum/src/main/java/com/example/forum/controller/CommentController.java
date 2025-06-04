package com.example.forum.controller;

import com.example.forum.dto.CommentRequestDto;
import com.example.forum.dto.CommentResponseDto;
import com.example.forum.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(
            summary = "Получить комментарии к посту",
            description = "Возвращает все комментарии, связанные с конкретным постом"
    )
    @ApiResponse(responseCode = "200", description = "Комментарии найдены",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Комментарии не найдены")
    @GetMapping("/post/{postId}")
    public List<CommentResponseDto> getCommentsByPost(
            @Parameter(description = "ID поста", example = "100")
            @PathVariable Long postId
    ) {
        log.info("Запрошены комментарии к посту id={}", postId);
        return commentService.getCommentsByPost(postId);
    }

    @Operation(
            summary = "Создать комментарий",
            description = "Создает новый комментарий для указанного поста"
    )
    @ApiResponse(responseCode = "200", description = "Комментарий создан",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Некорректные данные или несуществующий пост")
    @PostMapping
    public CommentResponseDto createComment(
            @RequestBody(
                    description = "Данные нового комментария",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CommentRequestDto.class))
            )
            @org.springframework.web.bind.annotation.RequestBody @Valid CommentRequestDto commentDto
    ) {
        log.info("Создание комментария: {}", commentDto);
        return commentService.createComment(commentDto);
    }
}