package com.linkee.linkeeapi.question.command.application.service;

import com.linkee.linkeeapi.question.command.application.dto.request.CreateQuestionRequestDto;

public interface QuestionCommandService {
    void createQuestion(CreateQuestionRequestDto request);
}
