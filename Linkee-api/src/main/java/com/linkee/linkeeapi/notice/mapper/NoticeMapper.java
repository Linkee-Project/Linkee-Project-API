package com.linkee.linkeeapi.notice.mapper;

import com.linkee.linkeeapi.inquiry.model.dto.response.InquiryResponseDto;
import com.linkee.linkeeapi.notice.model.dto.response.NoticeResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeMapper {
    List<NoticeResponseDto> findAll(@Param("offset") int offset,
                                    @Param("limit") int limit);

    int countAll();
}
