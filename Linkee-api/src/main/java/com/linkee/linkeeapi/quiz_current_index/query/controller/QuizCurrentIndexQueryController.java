package com.linkee.linkeeapi.quiz_current_index.query.controller;

import com.linkee.linkeeapi.common.model.dto.ApiResponse;
import com.linkee.linkeeapi.quiz_current_index.query.dto.response.CurrentQuestionResponse;
import com.linkee.linkeeapi.quiz_current_index.query.dto.response.QuizReconnectStateResponse;
import com.linkee.linkeeapi.quiz_current_index.query.service.QuizCurrentIndexQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quiz-rooms/{roomId}/current")
@RequiredArgsConstructor
public class QuizCurrentIndexQueryController {

    private final QuizCurrentIndexQueryService quizCurrentIndexQueryService;

    /* 현재 문제 인덱스 조회 */
    @GetMapping("/index")
    public ApiResponse<Integer> getCurrentIndex(@PathVariable Long roomId) {
        int index = quizCurrentIndexQueryService.getCurrentIndex(roomId);
        return ApiResponse.success(index);
    }

    /* 현재 문제 상세 조회 */
    @GetMapping("/question")
    public ApiResponse<CurrentQuestionResponse> getCurrentQuestion(@PathVariable Long roomId) {
        CurrentQuestionResponse response = quizCurrentIndexQueryService.getCurrentQuestion(roomId);
        return ApiResponse.success(response);
    }

    /* 재접속 복원용 전체 상태 조회 */
    @GetMapping("/reconnect-state")
    public ApiResponse<QuizReconnectStateResponse> getReconnectState(@PathVariable Long roomId) {
        QuizReconnectStateResponse response = quizCurrentIndexQueryService.getReconnectState(roomId);
        return ApiResponse.success(response);
    }
}
