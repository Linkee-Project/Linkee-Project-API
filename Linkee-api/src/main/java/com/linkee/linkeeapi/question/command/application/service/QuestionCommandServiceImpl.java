package com.linkee.linkeeapi.question.command.application.service;

import com.linkee.linkeeapi.category.command.aggregate.Category;
import com.linkee.linkeeapi.category.command.infrastructure.repository.CategoryRepository;
import com.linkee.linkeeapi.common.enums.Status;
import com.linkee.linkeeapi.question.command.application.dto.request.CreateQuestionRequestDto;
import com.linkee.linkeeapi.question.command.domain.aggregate.Question;
import com.linkee.linkeeapi.question.command.infrastructure.repository.JpaQuestionRepository;
import com.linkee.linkeeapi.user.command.application.service.util.UserFinder;
import com.linkee.linkeeapi.user.command.domain.entity.User;
import com.linkee.linkeeapi.user.command.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionCommandServiceImpl implements QuestionCommandService {

    private final JpaQuestionRepository jpaQuestionRepository;
    private final UserFinder userFinder;
    private final CategoryRepository categoryRepository;


    @Transactional
    public void createQuestion(CreateQuestionRequestDto request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(()-> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        Question question = Question.builder()
                .category(category)
                .questionTitle(request.getQuestionTitle())
                .questionQuestion(request.getQuestionQuestion())
                .questionAnswer(request.getQuestionAnswer())
                .user(userFinder.getById(request.getUserId()))
                .isQualified(Status.N)
                .isDeleted(Status.N)
                .questionViews(0L)
                .build();
        jpaQuestionRepository.save(question);

    }
}
