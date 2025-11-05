package com.linkee.linkeeapi.chat.chat_command.chat_domain.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomEventDto {
    private Long roomId;
    private String event; // "JOIN", "LEAVE", "CREATED", "DELETED"
    private String userNickname;
    private int currentMembers;
}
