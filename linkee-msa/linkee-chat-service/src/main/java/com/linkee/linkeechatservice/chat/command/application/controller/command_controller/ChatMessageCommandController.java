package com.linkee.linkeechatservice.chat.command.application.controller.command_controller;

import com.linkee.linkeechatservice.chat.command.application.service.command_service.ChatMessageCommandService;
import com.linkee.linkeechatservice.chat.command.domain.dto.command_dto.request.ChatMessageCreateRequest;
import com.linkee.linkeechatservice.chat.command.domain.dto.command_dto.request.MarkAsReadRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat-messages")
@Tag(name = "자율", description = "자율방(자유 출제방) 관련 API")
@Tag(name = "커뮤니케이션", description = "친구 및 채팅 기능 관련 API")
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
