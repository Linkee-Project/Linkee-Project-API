package com.linkee.linkeeapi.inquiry.controller;

import com.linkee.linkeeapi.common.model.PageResponse;
import com.linkee.linkeeapi.inquiry.model.dto.request.CreateInquiryRequestDto;
import com.linkee.linkeeapi.inquiry.model.dto.response.InquiryResponseDto;
import com.linkee.linkeeapi.inquiry.service.InquiryService;
import com.linkee.linkeeapi.user.model.entity.User;
import com.linkee.linkeeapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/inquiry")
public class InquiryController {
    private final InquiryService inquiryService;
    private final UserRepository userRepository;

    //create
    @PostMapping
    public ResponseEntity<String> createInquiry(@RequestBody CreateInquiryRequestDto request){
        inquiryService.createInquiry(request);
        return ResponseEntity.ok("문의사항 생성 완료");
    }

    //read
    @GetMapping()
    public ResponseEntity<PageResponse<InquiryResponseDto>> getInquiryList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) Integer size,
            @RequestParam Long userId)
/*            @AuthenticationPrinciple User currentUser)
            @AuthenticationPrinciple 는 spring Security 가 제공
             * 현재 로그인 User객체 주입*/
    {
        // DB에서 User 조회
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        PageResponse<InquiryResponseDto> response =
                inquiryService.getInquiryList(page, size, currentUser);

        return ResponseEntity.ok(response);
    }
}
