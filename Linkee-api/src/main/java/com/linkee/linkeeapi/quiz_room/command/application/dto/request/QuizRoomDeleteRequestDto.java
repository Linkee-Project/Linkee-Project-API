package com.linkee.linkeeapi.quiz_room.command.application.dto.request;

import lombok.Getter;

@Getter
public class QuizRoomDeleteRequestDto {
    private Long quizRoomId;
    private Long userId;
}
