package com.linkee.linkeeapi.report.controller;

import com.linkee.linkeeapi.report.model.dto.request.CreateReportRequestDto;
import com.linkee.linkeeapi.report.model.dto.request.ReadReportListRequestDto;
import com.linkee.linkeeapi.report.model.dto.response.ReportListResponseDto;
import com.linkee.linkeeapi.report.model.entity.Report;
import com.linkee.linkeeapi.report.repository.ReportRepository;
import com.linkee.linkeeapi.report.service.ReportService;
import com.linkee.linkeeapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/report")
public class ReportController {
    private final ReportService reportService;
    private final UserRepository userRepository;

    //create
    @PostMapping
    public ResponseEntity<String> createReport(@RequestBody CreateReportRequestDto request) {
        reportService.createReport(request);
        return ResponseEntity.ok("신고 생성 완료");
    }

    //조회
    @GetMapping
    public ResponseEntity<List<ReportListResponseDto>> getReportList(
            @RequestParam Long userId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        ReadReportListRequestDto request = ReadReportListRequestDto.builder()
                .userId(userId)
                .page(page)
                .size(size)
                .build();

        List<ReportListResponseDto> reports = reportService.getReportList(request);
        return ResponseEntity.ok(reports);
    }
}
