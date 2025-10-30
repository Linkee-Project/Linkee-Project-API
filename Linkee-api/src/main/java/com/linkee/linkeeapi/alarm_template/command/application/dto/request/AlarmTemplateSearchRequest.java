package com.linkee.linkeeapi.alarm_template.command.application.dto.request;

public record AlarmTemplateSearchRequest(
        String keyword,
        Integer page,
        Integer size,
        Integer offset
) {}
