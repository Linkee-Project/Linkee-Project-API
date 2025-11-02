package com.linkee.linkeeapi.question.command.application.dto.request;

import com.linkee.linkeeapi.user.command.domain.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateQuestionRequestDto {

    @NotBlank
    private Long userId; //작성자 ID

    @NotNull
    private Long categoryId;

    @NotBlank
    @Size(max = 100)
    private String questionTitle;

    @NotBlank
    private String questionQuestion;

    @NotNull
    private Integer questionAnswer;
}
