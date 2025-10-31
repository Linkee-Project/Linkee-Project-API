package com.linkee.linkeeapi.chat_room.command.application.controller;

import com.linkee.linkeeapi.chat_room.command.application.dto.request.ChatRoomCreateRequestDto;
import com.linkee.linkeeapi.chat_room.command.application.service.ChatRoomCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat-room")
public class ChatRoomCommandController {

    private final ChatRoomCommandService chatRoomCommandService;

    // 채팅방 / 게임방 생성
    @PostMapping
    public ResponseEntity<String> createRoom(@Valid @RequestBody ChatRoomCreateRequestDto request) {
        chatRoomCommandService.createRoom(request);
        return ResponseEntity.ok("채팅방이 성공적으로 생성되었습니다.");
    }
}
