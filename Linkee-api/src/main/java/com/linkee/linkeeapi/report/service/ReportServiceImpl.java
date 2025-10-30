package com.linkee.linkeeapi.report.service;

import com.linkee.linkeeapi.common.enums.Role;
import com.linkee.linkeeapi.inquiry.mapper.InquiryMapper;
import com.linkee.linkeeapi.report.mapper.ReportMapper;
import com.linkee.linkeeapi.report.model.dto.request.CreateReportRequestDto;
import com.linkee.linkeeapi.report.model.dto.request.ReadReportListRequestDto;
import com.linkee.linkeeapi.report.model.dto.response.ReportListResponseDto;
import com.linkee.linkeeapi.report.model.entity.Report;
import com.linkee.linkeeapi.report.repository.ReportRepository;
import com.linkee.linkeeapi.user.model.entity.User;
import com.linkee.linkeeapi.user.service.util.UserFinder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserFinder userFinder;
    private final ModelMapper modelMapper;
    private final ReportMapper reportMapper;

    //신고 등록
    @Override
    public void createReport(CreateReportRequestDto request){
        Report report = modelMapper.map(request, Report.class);
        report.setReportUser(userFinder.getById(request.getReporterId()));
        report.setReportedUser(userFinder.getById(request.getReportedId()));
        reportRepository.save(report);

    }

    //신고 목록 조회
    //페이징, 관리자(전체조회)/일반유저(자기 신고만 조회), (시간순, reportStatus, reportType)정렬방식도 추가
    public List<ReportListResponseDto> getReportList (ReadReportListRequestDto request) {

        User user = userFinder.getById(request.getUserId());  // DB에서 user 조회
        boolean isAdmin = false;
        if(user.getUserRole() == Role.ADMIN){
             isAdmin = true;
        }

        int page = request.getPage() != null ? request.getPage() : 0;
        int size = request.getSize() != null ? request.getSize() : 10;
        int offset = page * size;

        if (isAdmin) {
            return reportMapper.findAllReports(offset, size);
        } else {
            return reportMapper.findReportsByUser(request.getUserId(), offset, size);
        }

    }
}
