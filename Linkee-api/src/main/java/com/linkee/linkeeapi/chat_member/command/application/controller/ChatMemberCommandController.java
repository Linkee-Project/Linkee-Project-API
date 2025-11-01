package com.linkee.linkeeapi.chat_member.command.application.controller;

import com.linkee.linkeeapi.chat_member.command.application.dto.reqeust.ChatMemberCreateRequest;
import com.linkee.linkeeapi.chat_member.command.application.dto.response.ChatMemberCreateResponse;
import com.linkee.linkeeapi.chat_member.command.application.service.ChatMemberCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat_members")
public class ChatMemberCommandController {

    private final ChatMemberCommandService chatMemberCommandService;


    @PostMapping
    public ResponseEntity<String> createChatMember(@RequestBody ChatMemberCreateRequest request) {

        ChatMemberCreateResponse response = chatMemberCommandService.createChatMember(request);

        String message = "[" +response.getChatRoomId() +" | " + response.getChatRoomName() +"] 방 "
        + response.getUserNickName() + " 님이 입장하셨습니다.";

        return ResponseEntity.ok(message);
    }

}
