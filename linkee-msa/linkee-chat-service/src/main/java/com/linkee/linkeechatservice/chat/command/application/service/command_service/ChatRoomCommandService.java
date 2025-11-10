package com.linkee.linkeechatservice.chat.command.application.service.command_service;


import com.linkee.linkeechatservice.chat.chat_command.chat_domain.entity.ChatRoom;
import com.linkee.linkeechatservice.chat.command.domain.dto.command_dto.request.ChatRoomCreateRequestDto;
import com.linkee.linkeechatservice.chat.command.domain.dto.command_dto.request.ChatRoomDeleteRequestDto;
import com.linkee.linkeechatservice.user.entity.User;

public interface ChatRoomCommandService {

    //방 만들기
    ChatRoom createRoom(ChatRoomCreateRequestDto request);

    // 입장
    void enterRoom(Long roomId, User user);
    //방 삭제
    void deleteGameRoom(ChatRoomDeleteRequestDto request);

    void leaveRoom(ChatRoomDeleteRequestDto request);
}
