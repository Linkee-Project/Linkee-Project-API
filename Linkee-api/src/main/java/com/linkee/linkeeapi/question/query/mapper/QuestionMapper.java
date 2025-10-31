package com.linkee.linkeeapi.question.query.mapper;

import com.linkee.linkeeapi.question.query.model.dto.reponse.QuestionDetailResponseDto;
import com.linkee.linkeeapi.question.query.model.dto.reponse.QuestionListResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionMapper {

    // 문제 목록 조회  + (옵션) keyword
    List<QuestionListResponseDto> findAllWithKeyword(@Param("keyword") String keyword,
                                                     @Param("offset") int offset,
                                                     @Param("limit") int limit);
    int countAllWithKeyword(@Param("keyword") String keyword);

    // 카테고리별 조회 + (옵션) keyword
    List<QuestionListResponseDto> findByCategoryWithKeyword(@Param("categoryId") Long categoryId,
                                                            @Param("keyword") String keyword,
                                                            @Param("offset") int offset,
                                                            @Param("limit") int limit);
    int countByCategoryWithKeyword(@Param("categoryId") Long categoryId,
                                    @Param("keyword") String keyword);

    //문제 상세 조회
    QuestionDetailResponseDto findDetailByQuestionId(@Param("questionId") Long questionId);

    //조회수 증가
    void increaseViewCount(@Param("questionId") Long questionId);


}
