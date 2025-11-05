package com.linkee.linkeeapi.chat.command.application.controller.chat_controller;

import com.linkee.linkeeapi.chat.command.domain.dto.chat_dto.request.ChatRoomDto;
import com.linkee.linkeeapi.chat.command.domain.dto.chat_dto.response.ChatMessageDto;
import com.linkee.linkeeapi.chat.command.domain.entity.ChatMessageMongo;
import com.linkee.linkeeapi.chat.command.domain.entity.ChatRoom;
import com.linkee.linkeeapi.chat.command.domain.repository.ChatMessageMongoRepository;
import com.linkee.linkeeapi.chat.command.domain.repository.JpaChatRoomRepository;
import com.linkee.linkeeapi.common.security.model.CustomUser;
import com.linkee.linkeeapi.user.command.domain.entity.User;
import com.linkee.linkeeapi.user.command.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatRestController {

    private final JpaChatRoomRepository chatRoomRepository;
    private final ChatMessageMongoRepository chatMessageMongoRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/rooms")
    public List<ChatRoomDto> getRooms() {
        return chatRoomRepository.findAll().stream()
                .map(room -> ChatRoomDto.builder()
                        .roomId(room.getChatRoomId())
                        .roomName(room.getChatRoomName())
                        .roomOwnerName(room.getRoomOwner().getUserNickname())
                        .joinedCount(room.getJoinedCount())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("/room")
    public ChatRoomDto createRoom(@RequestParam String roomName, Principal principal) {
        CustomUser loginUser = (CustomUser)((Authentication)principal).getPrincipal();
        User owner = userRepository.findById(loginUser.getUserId()).orElseThrow();

        ChatRoom room = ChatRoom.builder()
                .chatRoomName(roomName)
                .roomOwner(owner)
                .joinedCount(1)
                .build();
        chatRoomRepository.save(room);

        return ChatRoomDto.builder()
                .roomId(room.getChatRoomId())
                .roomName(room.getChatRoomName())
                .roomOwnerName(owner.getUserNickname())
                .joinedCount(room.getJoinedCount())
                .build();
    }

    @MessageMapping("/message")
    public void sendMessage(ChatMessageDto dto, Principal principal) {
        if (principal == null) return;

        // principal.getName()로 이메일이나 userId 가져오기
        String userEmail = principal.getName();
        User sender = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ChatRoom room = chatRoomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        ChatMessageMongo msg = ChatMessageMongo.builder()
                .roomId(room.getChatRoomId())
                .senderId(sender.getUserId())
                .senderNickname(sender.getUserNickname())
                .message(dto.getMessage())
                .sentAt(LocalDateTime.now())
                .build();

        chatMessageMongoRepository.save(msg);
        messagingTemplate.convertAndSend("/topic/chatroom/" + room.getChatRoomId(), msg);
    }
}
