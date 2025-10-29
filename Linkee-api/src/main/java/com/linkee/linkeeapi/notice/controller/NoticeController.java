package com.linkee.linkeeapi.notice.controller;

import com.linkee.linkeeapi.common.model.PageResponse;
import com.linkee.linkeeapi.inquiry.model.dto.response.InquiryResponseDto;
import com.linkee.linkeeapi.notice.mapper.NoticeMapper;
import com.linkee.linkeeapi.notice.model.dto.request.CreateNoticeRequestDto;
import com.linkee.linkeeapi.notice.model.dto.response.NoticeResponseDto;
import com.linkee.linkeeapi.notice.model.entity.Notice;
import com.linkee.linkeeapi.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //공지사항 조회
    @GetMapping
    public ResponseEntity<PageResponse<NoticeResponseDto>> getNoticeList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) Integer size
    ){
        PageResponse<NoticeResponseDto> response =
                noticeService.getNoticeList(page, size);

        return ResponseEntity.ok(response);
    }
}
