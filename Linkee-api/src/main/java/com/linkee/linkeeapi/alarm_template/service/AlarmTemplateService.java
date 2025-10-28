package com.linkee.linkeeapi.alarm_template.service;

import com.linkee.linkeeapi.alarm_template.model.dto.request.AlarmTemplateCreateRequest;

public interface AlarmTemplateService {

    void createAlarmTemplate(AlarmTemplateCreateRequest request);
}
