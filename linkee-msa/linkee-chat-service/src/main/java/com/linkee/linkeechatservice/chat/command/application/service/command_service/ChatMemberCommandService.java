package com.linkee.linkeechatservice.chat.command.application.service.command_service;


import com.linkee.linkeechatservice.chat.command.domain.dto.command_dto.request.ChatMemberCreateRequest;
import com.linkee.linkeechatservice.chat.command.domain.dto.command_dto.response.ChatMemberCreateResponse;
import com.linkee.linkeechatservice.chat.command.domain.dto.command_dto.response.ChatMemberDeleteResponse;

public interface ChatMemberCommandService {

    ChatMemberCreateResponse createChatMember(ChatMemberCreateRequest request);
    void updateIsRead(Long chatMemberId);
    ChatMemberDeleteResponse deleteChatMember(Long userId, Long chatRoomId);
}
