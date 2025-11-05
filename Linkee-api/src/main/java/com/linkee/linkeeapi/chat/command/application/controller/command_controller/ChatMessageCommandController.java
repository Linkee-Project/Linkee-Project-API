package com.linkee.linkeeapi.chat.command.application.controller.command_controller;

import com.linkee.linkeeapi.chat.command.domain.dto.command_dto.request.ChatMessageCreateRequest;
import com.linkee.linkeeapi.chat.command.domain.dto.command_dto.request.MarkAsReadRequest;
import com.linkee.linkeeapi.chat.command.application.service.command_service.ChatMessageCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat-messages")
public class ChatMessageCommandController {

    private final ChatMessageCommandService chatMessageCommandService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody ChatMessageCreateRequest request){
        chatMessageCommandService.createChatMessage(request);
        System.out.println("==========" + request.toString());
        return ResponseEntity.ok("채팅 메세지 생성!");
    }

    //사용자가 채팅방 열어서 읽음 처리
    @PostMapping("/mark-as-read")
    public ResponseEntity<String> markAsRead(@Valid @RequestBody MarkAsReadRequest request) {
        chatMessageCommandService.markChatRoomAsRead(request);
        return ResponseEntity.ok("채팅방을 읽음 처리했습니다.");
    }
}
