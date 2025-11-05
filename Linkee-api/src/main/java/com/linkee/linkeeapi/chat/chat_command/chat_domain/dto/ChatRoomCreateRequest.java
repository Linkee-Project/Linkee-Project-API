package com.linkee.linkeeapi.chat.chat_command.chat_domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomCreateRequest {
    private String name;
    private String type;
    private boolean isPrivate;
}
