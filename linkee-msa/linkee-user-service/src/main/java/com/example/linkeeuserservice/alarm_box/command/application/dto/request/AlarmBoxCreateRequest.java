package com.example.linkeeuserservice.alarm_box.command.application.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlarmBoxCreateRequest {

    private String alarmBoxContent;
    private Long userId;

}
