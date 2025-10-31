package com.linkee.linkeeapi.question.query.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QuestionDetailResponseDto {
    private Long questionId;
    private String questionTitle;
    private String content;
    private String categoryName;
    private String userNickname;
    private int viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
