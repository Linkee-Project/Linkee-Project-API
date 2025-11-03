package com.linkee.linkeeapi.chat_member.command.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ChatMemberDeleteResponse {
    private Long chatRoomId;
    private String userNickName;
}
