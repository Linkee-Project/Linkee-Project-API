package com.linkee.linkeeapi.question.command.application.controller;

import com.linkee.linkeeapi.inquiry.command.application.service.InquiryCommandService;
import com.linkee.linkeeapi.question.command.application.dto.request.CreateQuestionRequestDto;
import com.linkee.linkeeapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/questions")
public class QuestionCommandController {

    private final QuestionCommandController questionService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<String> createQuestion (@RequestBody CreateQuestionRequestDto request){
        questionService.createQuestion(request);
        return  ResponseEntity.ok("문제 생성 완료");
    }

}
