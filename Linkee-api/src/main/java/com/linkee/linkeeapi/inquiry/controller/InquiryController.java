package com.linkee.linkeeapi.inquiry.controller;

import com.linkee.linkeeapi.inquiry.model.dto.request.CreateInquiryRequestDto;
import com.linkee.linkeeapi.inquiry.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/inquiry")
public class InquiryController {
    private final InquiryService inquiryService;

    //create
    @PostMapping
    public ResponseEntity<String> createInquiry(@RequestBody CreateInquiryRequestDto request){
        inquiryService.createInquiry(request);
        return ResponseEntity.ok("문의사항 생성 완료");
    }
}
