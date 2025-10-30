package com.linkee.linkeeapi.report.service;

import com.linkee.linkeeapi.report.model.dto.request.CreateReportRequestDto;

public interface ReportService {

    //create
    void createReport(CreateReportRequestDto request);
}
