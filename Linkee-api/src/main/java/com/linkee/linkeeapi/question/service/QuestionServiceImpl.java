package com.linkee.linkeeapi.question.service;

import com.linkee.linkeeapi.common.model.PageResponse;
import com.linkee.linkeeapi.question.mapper.QuestionMapper;
import com.linkee.linkeeapi.question.model.dto.reponse.QuestionDetailResponseDto;
import com.linkee.linkeeapi.question.model.dto.reponse.QuestionListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionMapper questionMapper;

    //문제 목록 조회 + 옵션(keyword)
    @Override
    public PageResponse<QuestionListResponseDto> getQuestionList(int page, Integer size, String keyword) {

        int pageSize = (size != null) ? size : 10;
        int offset = page * pageSize;

        String normalizeKeyword = normalizeKeyword(keyword);

        List<QuestionListResponseDto> questions =
                questionMapper.findAllWithKeyword(normalizeKeyword, offset, pageSize);
        int total = questionMapper.countAllWithKeyword(normalizeKeyword);

        return PageResponse.from(questions, page, pageSize, total);
    }
    //문제 카테고리별 조회 + 옵션(keyword)
    @Override
    public PageResponse<QuestionListResponseDto> getQuestionsListByCategory(int page, Integer size, Long categoryId, String keyword) {

        int pageSize = (size != null) ? size : 10;
        int offset = page * pageSize;

        String normalizeKeyword = normalizeKeyword(keyword);

        List<QuestionListResponseDto> questions =
                questionMapper.findByCategoryWithKeyword(categoryId, normalizeKeyword, offset, pageSize);

        int total = questionMapper.countByCategoryWithKeyword(categoryId, normalizeKeyword);

        return PageResponse.from(questions,page,pageSize,total);


    }
    //문제 상세 조회
    @Override
    @Transactional
    public QuestionDetailResponseDto getQuestionDetail(Long questionId) {
        questionMapper.increaseViewCount(questionId);
        return questionMapper.findDetailByQuestionId(questionId);
    }

    // LIKE 검색 안전처리: %, _, \ (null/blank는 null 반환)
    private String normalizeKeyword(String keyword) {
        if (keyword == null) return null;
        String trimmed = keyword.trim();
        if (trimmed.isEmpty()) return null;
        return trimmed
                .replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_");
    }
}
