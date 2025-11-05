package com.linkee.linkeeapi.chat.command.domain.dto.chat_dto.response;

import lombok.*;

@Getter
@Setter
@Builder
public class ChatMessageDto {
    private Long roomId;
    private String message;
}
