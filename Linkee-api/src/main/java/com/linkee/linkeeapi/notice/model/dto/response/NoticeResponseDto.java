package com.linkee.linkeeapi.notice.model.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeResponseDto {

    private String noticeTitle;
    private String noticeContent;
    private Long noticeViews;
    private LocalDateTime createdAt;

}
