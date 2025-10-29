package com.linkee.linkeeapi.alarm_template.service;

import com.linkee.linkeeapi.alarm_template.model.dto.request.AlarmTemplateCreateRequest;
import com.linkee.linkeeapi.alarm_template.model.entity.AlarmTemplate;
import com.linkee.linkeeapi.alarm_template.repository.AlarmTemplateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
class AlarmTemplateServiceImplTest {

    @Autowired
    private AlarmTemplateService service;
    @Autowired
    private AlarmTemplateRepository repository;

    @Test
    @DisplayName("알람 템플릿 생성")
    void saveAlarmTemplate() {
        String content = "템플릿 테스트 내용";
        AlarmTemplateCreateRequest request = new AlarmTemplateCreateRequest(content);

        service.createAlarmTemplate(request);

        // Repository로 실제 DB에 저장되었는지 확인
        AlarmTemplate saved = repository.findAll().get(0);

        assertThat(saved.getTemplateContent()).isEqualTo(content);
    }

}