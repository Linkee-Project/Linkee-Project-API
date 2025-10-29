package com.linkee.linkeeapi.alarm_template.model.dto.request;

public record AlarmTemplateSearchRequest(
        String keyword,
        Integer page,
        Integer size,
        Integer offset
) {}
