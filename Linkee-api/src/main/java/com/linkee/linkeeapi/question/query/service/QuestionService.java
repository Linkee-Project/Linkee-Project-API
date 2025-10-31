package com.linkee.linkeeapi.question.query.service;


import com.linkee.linkeeapi.common.model.PageResponse;
import com.linkee.linkeeapi.question.query.model.dto.reponse.QuestionDetailResponseDto;
import com.linkee.linkeeapi.question.query.model.dto.reponse.QuestionListResponseDto;

public interface QuestionService {

    PageResponse<QuestionListResponseDto> getQuestionList(int page, Integer size, String keyword);

    PageResponse<QuestionListResponseDto> getQuestionsListByCategory(int page, Integer size, Long categoryId, String keyword);

    QuestionDetailResponseDto getQuestionDetail(Long questionId);
}
