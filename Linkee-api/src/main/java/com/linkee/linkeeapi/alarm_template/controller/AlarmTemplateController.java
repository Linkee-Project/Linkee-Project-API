package com.linkee.linkeeapi.alarm_template.controller;

import com.linkee.linkeeapi.alarm_template.model.dto.request.AlarmTemplateCreateRequest;
import com.linkee.linkeeapi.alarm_template.service.AlarmTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ap1/v1/alarm_template")
public class AlarmTemplateController {

    private final AlarmTemplateService service;


    @PostMapping
    public ResponseEntity<String> createAlarmTemplate(@RequestBody String content){
        AlarmTemplateCreateRequest request = new AlarmTemplateCreateRequest(content);
        service.createAlarmTemplate(request);

        return ResponseEntity.ok("저장 완료");
    }

}
