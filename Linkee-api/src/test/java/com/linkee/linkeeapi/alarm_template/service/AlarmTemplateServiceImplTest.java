package com.linkee.linkeeapi.alarm_template.service;

import static org.junit.jupiter.api.Assertions.*;

import com.linkee.linkeeapi.alarm_template.model.dto.request.AlarmTemplateCreateRequest;
import com.linkee.linkeeapi.alarm_template.model.dto.request.AlarmTemplateSearchRequest;
import com.linkee.linkeeapi.alarm_template.model.dto.response.AlarmTemplateResponse;
import com.linkee.linkeeapi.alarm_template.model.entity.AlarmTemplate;
import com.linkee.linkeeapi.alarm_template.repository.AlarmTemplateRepository;
import com.linkee.linkeeapi.common.model.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    @DisplayName("알람 템플릿 조회 및 페이징 확인")
    void selectAllAlarmTemplate() {
        // given - 목록조회를 위한 더미데이터
        repository.save(new AlarmTemplate(null, "템플릿1"));
        repository.save(new AlarmTemplate(null, "템플릿2"));
        repository.save(new AlarmTemplate(null, "템플릿3"));

        AlarmTemplateSearchRequest searchRequest = new AlarmTemplateSearchRequest(null, 0, 10 ,0);

        // when
        PageResponse<AlarmTemplateResponse> pageResponse = service.selectAllAlarmTemplate(searchRequest);

        // then
        assertThat(pageResponse.getTotalElements()).isEqualTo(3);
        assertThat(pageResponse.getContent().get(0).templateContent()).isEqualTo("템플릿3");
    }

}