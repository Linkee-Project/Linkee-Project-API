package com.linkee.linkeeapi.chat.chat_command.chat_controller;

import com.linkee.linkeeapi.chat.chat_command.chat_domain.dto.ChatMessageDto;
import com.linkee.linkeeapi.chat.chat_command.chat_domain.entity.ChatMessageMongo;
import com.linkee.linkeeapi.chat.chat_command.chat_repository.ChatMessageMongoRepository;
import com.linkee.linkeeapi.chat.chat_command.chat_service.ChatRoomInOutService;
import com.linkee.linkeeapi.common.security.jwt.JwtTokenProvider;
import com.linkee.linkeeapi.user.command.domain.entity.User;
import com.linkee.linkeeapi.user.command.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;


@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatMessageMongoRepository chatMessageMongoRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomInOutService chatRoomInOutService;
    // 메시지 전송
    @MessageMapping("/chat.send")
    @SendTo("/topic/chatroom")
    public ChatMessageDto sendMessage(ChatMessageDto messageDto,
                                      @Header("Authorization") String token) {

        User sender = validateTokenAndGetUser(token);

        messageDto.setSenderId(sender.getUserId());
        messageDto.setSenderNickname(sender.getUserNickname());
        messageDto.setSentAt(LocalDateTime.now());

        chatMessageMongoRepository.save(ChatMessageMongo.builder()
                .roomId(messageDto.getRoomId())
                .senderId(sender.getUserId())
                .senderNickname(sender.getUserNickname())
                .message(messageDto.getMessage())
                .sentAt(messageDto.getSentAt())
                .build());

        System.out.println(">>> 메시지 도착: " + messageDto.getMessage());
        System.out.println(">>> 유저: " + sender.getUserNickname());

        return messageDto;
    }



    @MessageMapping("/chat.join")
    @SendTo("/topic/chatroom")
    public ChatMessageDto joinRoom(@Header("roomId") Long roomId,
                                   @Header("Authorization") String token,
                                   @Header(value = "roomCode", required = false) Integer roomCode) {
        // ChatRoomInOutService의 joinRoom 메서드와 맞춤
        return chatRoomInOutService.joinRoom(roomId, token, roomCode);
    }

    // 방 퇴장
    @MessageMapping("/chat.leave")
    @SendTo("/topic/chatroom")
    public ChatMessageDto leaveRoom(@Header("roomId") Long roomId,
                                    @Header("Authorization") String token) {
        return chatRoomInOutService.leaveRoom(roomId, token);
    }



    private User validateTokenAndGetUser(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new RuntimeException("Unauthorized");
        }
        String userEmail = jwtTokenProvider.getUsername(token);
        return userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}