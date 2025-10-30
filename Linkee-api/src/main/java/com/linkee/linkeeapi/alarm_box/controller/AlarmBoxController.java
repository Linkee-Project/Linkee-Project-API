package com.linkee.linkeeapi.alarm_box.controller;

import com.linkee.linkeeapi.alarm_box.model.dto.request.AlarmBoxCreateRequest;
import com.linkee.linkeeapi.alarm_box.model.dto.request.AlarmBoxSearchRequest;
import com.linkee.linkeeapi.alarm_box.model.dto.response.AlarmBoxResponse;
import com.linkee.linkeeapi.alarm_box.service.AlarmBoxService;
import com.linkee.linkeeapi.alarm_template.model.dto.request.AlarmTemplateSearchRequest;
import com.linkee.linkeeapi.alarm_template.model.dto.response.AlarmTemplateResponse;
import com.linkee.linkeeapi.common.model.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ap1/v1/alarm_boxes")
public class AlarmBoxController {

    private final AlarmBoxService service;

    @GetMapping
    public PageResponse<AlarmBoxResponse> getAllAlarmTemplates(
            AlarmBoxSearchRequest request) {

        return service.selectAllAlarmBox(request);
    }

    @GetMapping("/{alarmBoxId}")
    public ResponseEntity<AlarmBoxResponse> getAlarmBoxById(@PathVariable long alarmBoxId){

        return service.selectAlarmTemplateByAlarmBoxId(alarmBoxId);
    }

    @PostMapping
    public ResponseEntity<String> createAlarmBoxContent(@RequestBody AlarmBoxCreateRequest request){
        service.createAlarmBox(request);
        return ResponseEntity.ok("알람박스 생성 성공");
    }


    @PatchMapping("/check/{alarmBoxId}")
    public ResponseEntity<String> checkedAlarm(@PathVariable Long alarmBoxId){
        service.checkedAlarmBox(alarmBoxId);

        return ResponseEntity.ok("알림확인!");
    }

    @DeleteMapping("/delete/{alarmBoxId}")
    public ResponseEntity<String> deleteAlarm(@PathVariable Long alarmBoxId){
        service.deleteAlarmBoxById(alarmBoxId);
        return ResponseEntity.ok("삭제 성공");
    }

}
