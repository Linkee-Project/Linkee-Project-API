package com.linkee.linkeeapi.alarm_box.model.dto.request;

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
