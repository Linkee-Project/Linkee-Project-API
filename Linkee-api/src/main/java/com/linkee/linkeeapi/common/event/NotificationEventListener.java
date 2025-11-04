package com.linkee.linkeeapi.common.event;

import com.linkee.linkeeapi.alarm_box.command.application.dto.request.AlarmBoxCreateRequest;
import com.linkee.linkeeapi.alarm_box.command.application.service.AlarmBoxCommandService;
import com.linkee.linkeeapi.alarm_template.query.dto.response.AlarmTemplateResponse;
import com.linkee.linkeeapi.alarm_template.query.mapper.AlarmTemplateMapper;
import com.linkee.linkeeapi.common.sse.service.SseService;
import com.linkee.linkeeapi.user.command.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {
    private final AlarmBoxCommandService alarmBoxCommandService;
    private final AlarmTemplateMapper alarmTemplateMapper;
    private final SseService sseService;

    @EventListener
    public void handleFriendRequestEvent(FriendRequestEvent event) {
        User requester = event.getRequester();
        User receiver = event.getReceiver();

        AlarmTemplateResponse alarmTemplate = alarmTemplateMapper.selectAlarmTemplateById(1L);
        String alarmContent = String.format("%s%s", requester.getUserNickname(), alarmTemplate.templateContent());

        AlarmBoxCreateRequest alarmBoxCreateRequest = AlarmBoxCreateRequest.builder()
                .alarmBoxContent(alarmContent)
                .userId(receiver.getUserId())
                .build();
        alarmBoxCommandService.createAlarmBox(alarmBoxCreateRequest);

        sseService.send(receiver.getUserId(), "newNotification", alarmContent);
    }



}
