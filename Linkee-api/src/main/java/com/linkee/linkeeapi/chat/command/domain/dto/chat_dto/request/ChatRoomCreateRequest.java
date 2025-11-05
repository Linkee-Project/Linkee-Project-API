package com.linkee.linkeeapi.chat.command.domain.dto.chat_dto.request;

import lombok.Data;

@Data
public class ChatRoomCreateRequest {
    private String roomName;
    private Integer roomCapacity;
}
