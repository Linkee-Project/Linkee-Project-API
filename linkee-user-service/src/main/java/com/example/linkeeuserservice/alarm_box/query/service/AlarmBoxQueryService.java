package com.example.linkeeuserservice.alarm_box.query.service;


import com.example.linkeeuserservice.alarm_box.query.dto.request.AlarmBoxSearchRequest;
import com.example.linkeeuserservice.alarm_box.query.dto.response.AlarmBoxResponse;
import com.example.linkeeuserservice.common.model.PageResponse;
import org.springframework.http.ResponseEntity;

public interface AlarmBoxQueryService {

    PageResponse<AlarmBoxResponse> selectAllAlarmBox(AlarmBoxSearchRequest request);
    ResponseEntity<AlarmBoxResponse> selectAlarmTemplateByAlarmBoxId(Long alarmBoxId);

}
