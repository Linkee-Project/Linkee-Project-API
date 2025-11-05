package com.linkee.linkeeapi.chat.chat_command.chat_domain.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    private String senderNickname;
    private String message;
}
