package com.linkee.linkeeapi.chat.chat_command.chat_repository;


import com.linkee.linkeeapi.chat.chat_command.chat_domain.dto.ChatMemberDto;
import com.linkee.linkeeapi.chat.chat_command.chat_domain.entity.ChatMember;

public class ChatMemberMapper {
    public static ChatMemberDto toDto(ChatMember member) {
        return new ChatMemberDto(
                member.getChatMemberId(),
                member.getUser().getUserId(),
                member.getUser().getUserNickname(),
                member.getJoinedAt(),
                member.getLeftAt()
        );
    }
}
