package com.example.linkeeuserservice.alarm_template.command.application.service;


import com.example.linkeeuserservice.alarm_template.command.application.dto.request.AlarmTemplateCreateRequest;

public interface AlarmTemplateCommandService {

    void createAlarmTemplate(AlarmTemplateCreateRequest request);
    void modifyAlarmTemplateByAlarmTemplateId(Long templateId,AlarmTemplateCreateRequest request);

}
