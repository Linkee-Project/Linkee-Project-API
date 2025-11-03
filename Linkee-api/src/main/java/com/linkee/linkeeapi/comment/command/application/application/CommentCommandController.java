package com.linkee.linkeeapi.comment.command.application.application;

import com.linkee.linkeeapi.comment.command.application.dto.request.CreateCommentRequestDto;
import com.linkee.linkeeapi.comment.command.application.dto.response.CreateCommentResponseDto;
import com.linkee.linkeeapi.comment.command.application.service.CommentCommandService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/questions/{questionId}/comments")
public class CommentCommandController {

    private final CommentCommandService commentCommandService;


    // 댓글 등록
    @PostMapping
    public ResponseEntity<CreateCommentResponseDto> create(
            @PathVariable Long questionId,
            @RequestParam Long userId, // 임시 (JWT 적용 전)
            @Valid @RequestBody CreateCommentRequestDto request) {

        CreateCommentResponseDto response = commentCommandService.createComment(questionId, userId, request);
        return ResponseEntity.ok(response);
    }
}
