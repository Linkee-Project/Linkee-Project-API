package com.linkee.linkeechatservice.chat.command.application.service.query_serivce;


import com.linkee.linkeechatservice.chat.command.domain.dto.query_dto.request.ChatMessageSearchRequest;
import com.linkee.linkeechatservice.chat.command.domain.dto.query_dto.response.ChatMessageResponse;
import com.linkee.linkeechatservice.common.model.PageResponse;

public interface ChatMessageQueryService {

    PageResponse<ChatMessageResponse> selectAllChatMessage(ChatMessageSearchRequest request);
}
