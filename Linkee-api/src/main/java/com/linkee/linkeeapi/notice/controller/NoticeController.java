package com.linkee.linkeeapi.notice.controller;

import com.linkee.linkeeapi.notice.model.dto.request.CreateNoticeRequestDto;
import com.linkee.linkeeapi.notice.model.entity.Notice;
import com.linkee.linkeeapi.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notice")
public class NoticeController {

    private final NoticeService noticeService;

    //공지사항 등록
    //admin아니면 오류 던지기
    @PostMapping
    public ResponseEntity<String> createNotice(@RequestBody CreateNoticeRequestDto request){
        noticeService.createNotice(request);
        return ResponseEntity.ok("공지사항 생성 완료");
    }
}
