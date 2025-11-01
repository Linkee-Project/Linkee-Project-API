package com.linkee.linkeeapi.question.command.application.service;

import com.linkee.linkeeapi.question.command.application.dto.request.CreateQuestionRequestDto;
import com.linkee.linkeeapi.question.command.infrastructure.repository.JpaQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionCommandServiceImpl implements QuestionCommandService {

    private final JpaQuestionRepository jpaQuestionRepository;

    public void createQuestion(CreateQuestionRequestDto request) {

    }
}
