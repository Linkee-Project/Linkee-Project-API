package com.linkee.linkeeapi.question.service;

import com.linkee.linkeeapi.common.model.PageResponse;
import com.linkee.linkeeapi.question.query.mapper.QuestionMapper;
import com.linkee.linkeeapi.question.query.model.dto.reponse.QuestionDetailResponseDto;
import com.linkee.linkeeapi.question.query.model.dto.reponse.QuestionListResponseDto;
import com.linkee.linkeeapi.question.query.service.QuestionServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Mock
    private QuestionMapper questionMapper;

    @Test
    @DisplayName("문제 목록 조회 - 키워드 없음")
    void getQuestionList_NoKeyword() {
        // given
        int page = 0;
        int size = 10;
        String keyword = null;

        QuestionListResponseDto question1 = new QuestionListResponseDto();
        QuestionListResponseDto question2 = new QuestionListResponseDto();
        List<QuestionListResponseDto> mockQuestions = Arrays.asList(question1, question2);
        int mockTotal = 2;

        when(questionMapper.findAllWithKeyword(isNull(), anyInt(), anyInt())).thenReturn(mockQuestions);
        when(questionMapper.countAllWithKeyword(isNull())).thenReturn(mockTotal);

        // when
        PageResponse<QuestionListResponseDto> result = questionService.getQuestionList(page, size, keyword);

        // then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(mockTotal);
        assertThat(result.getCurrentPage()).isEqualTo(page);
        assertThat(result.getSize()).isEqualTo(size);
        verify(questionMapper).findAllWithKeyword(isNull(), eq(0), eq(10));
        verify(questionMapper).countAllWithKeyword(isNull());
    }

    @Test
    @DisplayName("카테고리별 문제 목록 조회 - 키워드 있음")
    void getQuestionsListByCategory() {
        // given
        int page = 0;
        int size = 5;
        Long categoryId = 1L;
        String keyword = "Java";

        QuestionListResponseDto question1 = new QuestionListResponseDto();
        List<QuestionListResponseDto> mockQuestions = List.of(question1);
        int mockTotal = 1;

        when(questionMapper.findByCategoryWithKeyword(anyLong(), anyString(), anyInt(), anyInt())).thenReturn(mockQuestions);
        when(questionMapper.countByCategoryWithKeyword(anyLong(), anyString())).thenReturn(mockTotal);

        // when
        PageResponse<QuestionListResponseDto> result = questionService.getQuestionsListByCategory(page, size, categoryId, keyword);

        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getTotalElements()).isEqualTo(mockTotal);
        assertThat(result.getCurrentPage()).isEqualTo(page);
        assertThat(result.getSize()).isEqualTo(size);
        verify(questionMapper).findByCategoryWithKeyword(eq(categoryId), contains(keyword), eq(0), eq(5));
        verify(questionMapper).countByCategoryWithKeyword(eq(categoryId), contains(keyword));
    }

    @Test
    @DisplayName("문제 상세 조회 - 조회수 증가 로직 확인")
    void getQuestionDetail_VerifyViewCountIncrease() {
        // given
        Long questionId = 1L;
        QuestionDetailResponseDto mockDetail = new QuestionDetailResponseDto();
        mockDetail.setQuestionId(questionId);
        mockDetail.setQuestionTitle("Test Title");

        when(questionMapper.findDetailByQuestionId(questionId)).thenReturn(mockDetail);

        // when
        QuestionDetailResponseDto result = questionService.getQuestionDetail(questionId);

        // then
        // 1. 조회수 증가 메소드가 정확한 questionId로 1번 호출되었는지 검증
        verify(questionMapper).increaseViewCount(questionId);

        // 2. 상세 조회 메소드가 정확한 questionId로 1번 호출되었는지 검증
        verify(questionMapper).findDetailByQuestionId(questionId);

        // 3. 반환된 결과가 예상과 일치하는지 검증
        assertThat(result).isNotNull();
        assertThat(result.getQuestionId()).isEqualTo(questionId);
        assertThat(result.getQuestionTitle()).isEqualTo("Test Title");
    }
}
