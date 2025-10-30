package com.linkee.linkeeapi.report.mapper;

import com.linkee.linkeeapi.report.model.dto.response.ReportListResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportMapper {

    // 전체 신고 목록 조회 (관리자용)
    List<ReportListResponseDto> findAllReports(
            @Param("offset") int offset,
            @Param("limit") int limit);

    // 사용자 신고 목록 조회 (본인 신고만)
    List<ReportListResponseDto> findReportsByUser(
            @Param("userId") Long userId,
            @Param("offset") int offset,
            @Param("limit") int limit);
}
