package com.linkee.linkeeapi.chat_member.command.application.controller;

import com.linkee.linkeeapi.chat_member.command.application.dto.reqeust.ChatMemberCreateRequest;
import com.linkee.linkeeapi.chat_member.command.application.dto.response.ChatMemberCreateResponse;
import com.linkee.linkeeapi.chat_member.command.application.service.ChatMemberCommandService;
import com.linkee.linkeeapi.user.command.domain.entity.User;
import com.linkee.linkeeapi.user.command.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat_members")
public class ChatMemberCommandController {

    private final ChatMemberCommandService chatMemberCommandService;
    private final UserRepository userRepository;


    // create
    @PostMapping
    public ResponseEntity<String> createChatMember(@RequestBody ChatMemberCreateRequest request) {

        ChatMemberCreateResponse response = chatMemberCommandService.createChatMember(request);

        String message = "[" +response.getChatRoomId() +" | " + response.getChatRoomName() +"] 방 "
        + response.getUserNickName() + " 님이 입장하셨습니다.";

        return ResponseEntity.ok(message);
    }

    //updateIsRead
    @PatchMapping("/read/{chatMemberId}")
    public ResponseEntity<String> updateIsRead(@PathVariable Long chatMemberId) {
        chatMemberCommandService.updateIsRead(chatMemberId);
        return ResponseEntity.ok("읽음 처리 완료");
    }

    //deleteChatMember -> 나가면 leftAt 시간을 update처리
    @DeleteMapping("/leave")
    public ResponseEntity<String> leaveRoom(
            @RequestParam Long userId,
            @RequestParam Long chatRoomId) {

        User found = userRepository.findById(userId).orElseThrow();

        chatMemberCommandService.deleteChatMember(userId, chatRoomId);

        return ResponseEntity.ok(chatRoomId + "번 방" + found.getUserNickname() + " 님 이 퇴장 하였습니다");
    }

}
