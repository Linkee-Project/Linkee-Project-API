package com.linkee.linkeeapi.question.query.model.dto.reponse;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QuestionListResponseDto {
    private Long questionId;
    private String questionTitle;
    private String categoryName;
    private String userNickname;
    private Integer viewCount;
    private LocalDateTime createdAt;

}
