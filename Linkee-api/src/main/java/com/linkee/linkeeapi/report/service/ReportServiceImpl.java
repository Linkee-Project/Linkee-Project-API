package com.linkee.linkeeapi.report.service;

import com.linkee.linkeeapi.inquiry.mapper.InquiryMapper;
import com.linkee.linkeeapi.report.model.dto.request.CreateReportRequestDto;
import com.linkee.linkeeapi.report.model.entity.Report;
import com.linkee.linkeeapi.report.repository.ReportRepository;
import com.linkee.linkeeapi.user.service.util.UserFinder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserFinder userFinder;
    private final ModelMapper modelMapper;
    private final InquiryMapper inquiryMapper;

    //신고 등록
    @Override
    public void createReport(CreateReportRequestDto request){
        Report report = modelMapper.map(request, Report.class);
        report.setReportUser(userFinder.getById(request.getReporterId()));
        report.setReportedUser(userFinder.getById(request.getReportedId()));
        reportRepository.save(report);

    }
}
