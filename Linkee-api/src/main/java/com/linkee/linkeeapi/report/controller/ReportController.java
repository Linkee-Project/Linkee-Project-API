package com.linkee.linkeeapi.report.controller;

import com.linkee.linkeeapi.report.model.dto.request.CreateReportRequestDto;
import com.linkee.linkeeapi.report.model.entity.Report;
import com.linkee.linkeeapi.report.repository.ReportRepository;
import com.linkee.linkeeapi.report.service.ReportService;
import com.linkee.linkeeapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
