package com.linkee.linkeeapi.chat.chat_command.chat_controller;

import com.linkee.linkeeapi.chat.chat_command.chat_domain.dto.ChatMessageDto;
import com.linkee.linkeeapi.chat.chat_command.chat_domain.entity.ChatMember;
import com.linkee.linkeeapi.chat.chat_command.chat_domain.entity.ChatMessageMongo;
import com.linkee.linkeeapi.chat.chat_command.chat_domain.entity.ChatRoom;
import com.linkee.linkeeapi.chat.chat_command.chat_domain.entity.ChatRoomType;
import com.linkee.linkeeapi.chat.chat_command.chat_repository.ChatMemberRepository;
import com.linkee.linkeeapi.chat.chat_command.chat_repository.ChatMessageMongoRepository;
import com.linkee.linkeeapi.chat.chat_command.chat_repository.ChatRoomRepository;
import com.linkee.linkeeapi.chat.chat_command.chat_service.ChatRoomInOutService;
import com.linkee.linkeeapi.chat.chat_command.chat_service.ChatService;
import com.linkee.linkeeapi.common.exception.BusinessException;
import com.linkee.linkeeapi.common.exception.ErrorCode;
import com.linkee.linkeeapi.common.security.jwt.JwtTokenProvider;
import com.linkee.linkeeapi.user.command.domain.entity.User;
import com.linkee.linkeeapi.user.command.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatMessageMongoRepository chatMessageMongoRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomInOutService chatService;
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
                                   @Header("Authorization") String token) {
        return chatService.joinRoom(roomId, token);
    }

    @MessageMapping("/chat.leave")
    @SendTo("/topic/chatroom")
    public ChatMessageDto leaveRoom(@Header("roomId") Long roomId,
                                    @Header("Authorization") String token) {
        return chatService.leaveRoom(roomId, token);
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
