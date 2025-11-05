package com.linkee.linkeeapi.chat.command.domain.dto.chat_dto.request;


import lombok.*;

@Getter
@Setter
@Builder
public class ChatRoomDto {
    private Long roomId;
    private String roomName;
    private String roomOwnerName;
    private Integer joinedCount;
}
