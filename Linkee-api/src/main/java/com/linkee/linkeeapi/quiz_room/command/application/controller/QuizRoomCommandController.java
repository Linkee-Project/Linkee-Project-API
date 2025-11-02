package com.linkee.linkeeapi.quiz_room.command.application.controller;

import com.linkee.linkeeapi.common.ApiResponse;
import com.linkee.linkeeapi.quiz_room.command.application.service.QuizRoomCommandService;
import com.linkee.linkeeapi.quiz_room.command.application.dto.request.CreateQuizRoomRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 퀴즈룸 생성 관련 HTTP 요청을 처리하는 컨트롤러.
 * 클라이언트의 요청을 받아 QuizRoomCommandService로 전달합니다.
 */
@RestController
@RequestMapping("/api/v1/quiz-rooms")
@RequiredArgsConstructor
public class QuizRoomCommandController {

    private final QuizRoomCommandService quizRoomCommandService;

    /*
     * 새로운 퀴즈룸을 생성합니다.
     * @param request 퀴즈룸 생성에 필요한 데이터를 담은 DTO
     * @return 생성된 퀴즈룸의 ID를 포함하는 API 응답
     */
    @PostMapping
    public ApiResponse<Long> createQuizRoom(@RequestBody CreateQuizRoomRequest request) {
        //  추후 Spring Security가 적용되면 @AuthenticationPrincipal 에서 유저 정보를 받아와야 합니다.
        Long userId = 1L; // 임시로 방장(유저) ID를 1로 설정합니다.
        
        Long quizRoomId = quizRoomCommandService.create(request, userId);
        return ApiResponse.success(quizRoomId);
    }
}
