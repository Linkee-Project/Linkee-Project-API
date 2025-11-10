package com.linkee.linkeechatservice.chat.command.application.service.query_serivce;


import com.linkee.linkeechatservice.chat.command.domain.dto.query_dto.request.ChatRoomListRequestDto;
import com.linkee.linkeechatservice.chat.command.domain.dto.query_dto.request.GameRoomListRequestDto;
import com.linkee.linkeechatservice.chat.command.domain.dto.query_dto.response.ChatRoomListResponseDto;
import com.linkee.linkeechatservice.chat.command.domain.dto.query_dto.response.GameRoomListResponseDto;
import com.linkee.linkeechatservice.common.model.PageResponse;

public interface ChatRoomQueryService {

    //자신의 채팅방 목록 조회
    PageResponse<ChatRoomListResponseDto> getMyChatRoomList(ChatRoomListRequestDto request);

    //게임방 목록 전체 조회
    PageResponse<GameRoomListResponseDto> getGameRoomList(GameRoomListRequestDto request);
}
