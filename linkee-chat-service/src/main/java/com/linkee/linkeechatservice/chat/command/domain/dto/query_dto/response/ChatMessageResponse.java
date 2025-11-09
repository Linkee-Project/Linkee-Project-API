package com.linkee.linkeechatservice.chat.command.domain.dto.query_dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageResponse {

    private Long chatMessageId;
    private String MessageContent;
    private LocalDateTime sentAt;
    private Long chatRoomId;
    private Long senderId;

}
