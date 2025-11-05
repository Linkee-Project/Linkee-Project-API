package com.linkee.linkeeapi.chat.command.application.service.command_service;

import com.linkee.linkeeapi.chat.command.domain.dto.command_dto.request.ChatRoomCreateRequestDto;
import com.linkee.linkeeapi.chat.command.domain.dto.command_dto.request.ChatRoomDeleteRequestDto;

public interface ChatRoomCommandService {

    //방 만들기
    void createRoom(ChatRoomCreateRequestDto request);

    //방 삭제
    void deleteGameRoom(ChatRoomDeleteRequestDto request);

    void leaveRoom(ChatRoomDeleteRequestDto request);
}
