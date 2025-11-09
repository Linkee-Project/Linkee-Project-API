package com.linkee.linkeechatservice.chat.command.application.service.query_serivce;


import com.linkee.linkeechatservice.chat.command.domain.dto.query_dto.request.ChatMemberSearchRequest;
import com.linkee.linkeechatservice.chat.command.domain.dto.query_dto.response.ChatMemberResponse;
import com.linkee.linkeechatservice.common.model.PageResponse;

public interface ChatMemberQueryService {

    PageResponse<ChatMemberResponse> selectAllChatMember(ChatMemberSearchRequest request);
}
