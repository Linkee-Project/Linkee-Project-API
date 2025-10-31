package com.linkee.linkeeapi.chat_room.command.application.service;

import com.linkee.linkeeapi.chat_room.command.application.dto.request.ChatRoomCreateRequestDto;

public interface ChatRoomCommandService {

    //방 만들기
    void createRoom(ChatRoomCreateRequestDto request);

    //방 삭제  -> 방장이 나가면 자동으로 삭제
}
