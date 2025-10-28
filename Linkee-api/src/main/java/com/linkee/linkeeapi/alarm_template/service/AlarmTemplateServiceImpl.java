package com.linkee.linkeeapi.alarm_template.service;

import com.linkee.linkeeapi.alarm_template.model.dto.request.AlarmTemplateCreateRequest;
import com.linkee.linkeeapi.alarm_template.model.entity.AlarmTemplate;
import com.linkee.linkeeapi.alarm_template.repository.AlarmTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmTemplateServiceImpl implements AlarmTemplateService{

    private final AlarmTemplateRepository repository;


    @Override
    public void createAlarmTemplate(AlarmTemplateCreateRequest request) {

       AlarmTemplate alarmTemplate = AlarmTemplate.builder()
               .templateContent(request.templateContent())
               .build();

        repository.save(alarmTemplate);
    }
}
