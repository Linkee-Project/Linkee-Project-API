package com.linkee.linkeeapi.chat_member.command.application.service;

import com.linkee.linkeeapi.chat_member.command.application.dto.reqeust.ChatMemberCreateRequest;
import com.linkee.linkeeapi.chat_member.command.application.dto.response.ChatMemberCreateResponse;
import com.linkee.linkeeapi.chat_member.command.application.dto.response.ChatMemberDeleteResponse;

public interface ChatMemberCommandService {

    ChatMemberCreateResponse createChatMember(ChatMemberCreateRequest request);
    void updateIsRead(Long chatMemberId);
    ChatMemberDeleteResponse deleteChatMember(Long userId, Long chatRoomId);
}
