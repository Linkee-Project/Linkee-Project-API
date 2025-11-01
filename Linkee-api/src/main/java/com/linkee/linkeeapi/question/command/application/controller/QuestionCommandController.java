package com.linkee.linkeeapi.question.command.application.controller;

import com.linkee.linkeeapi.question.command.application.dto.request.CreateQuestionRequestDto;
import com.linkee.linkeeapi.question.command.application.service.QuestionCommandService;
import com.linkee.linkeeapi.user.command.infrastructure.repository.UserRepository;
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

    private final QuestionCommandService questionCommandService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<String> createQuestion (@RequestBody CreateQuestionRequestDto request){
        questionCommandService.createQuestion(request);
        return  ResponseEntity.ok("문제 등록 완료");
    }

}
