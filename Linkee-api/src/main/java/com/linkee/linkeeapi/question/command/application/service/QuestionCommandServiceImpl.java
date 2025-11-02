package com.linkee.linkeeapi.question.command.application.service;

import com.linkee.linkeeapi.category.command.aggregate.Category;
import com.linkee.linkeeapi.category.command.infrastructure.repository.CategoryRepository;
import com.linkee.linkeeapi.common.enums.Status;
import com.linkee.linkeeapi.question.command.application.dto.request.CreateQuestionRequestDto;
import com.linkee.linkeeapi.question.command.domain.aggregate.Question;
import com.linkee.linkeeapi.question.command.infrastructure.repository.JpaQuestionRepository;
import com.linkee.linkeeapi.question_option.command.domain.aggregate.QuestionOption;
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

        User user = userFinder.getById(request.getUserId());
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

        // Question 생성
        Question question = Question.builder()
                .category(category)
                .questionTitle(request.getQuestionTitle())
                .questionQuestion(request.getQuestionQuestion())
                .questionAnswer(request.getQuestionAnswer())
                .user(user)
                .isQualified(Status.N)
                .isDeleted(Status.N)
                .questionViews(0L)
                .build();
        // Question_Option 생성
        if (request.getOptions() != null) {
            for (CreateQuestionRequestDto.OptionDto od : request.getOptions()) {
                QuestionOption option = QuestionOption.builder()
                        .optionIndex(od.getIndex())
                        .optionText(od.getText())
                        .isCorrected(od.getIndex().equals(request.getQuestionAnswer()) ? Status.Y : Status.N)
                        .build();
                question.addOption(option); // addOption 내부에서 option.setQuestion(this) 수행
            }
        }

        jpaQuestionRepository.save(question);


    }
}
