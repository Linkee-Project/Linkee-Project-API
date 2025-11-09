package com.linkee.linkeechatservice.chat.chat_command.chat_controller;

import com.linkee.linkeechatservice.chat.chat_command.chat_domain.dto.ChatMessageDto;
import com.linkee.linkeechatservice.chat.chat_command.chat_service.ChatRoomInOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatRoomInOutService chatRoomInOutService;
    private final SimpMessagingTemplate messagingTemplate;

    // 메시지 전송
    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessageDto messageDto,
                            @Header("X-User-Id") Long userId,
                            @Header("X-User-Nickname") String nickname) {

        messageDto.setSenderId(userId);
        messageDto.setSenderNickname(nickname);
        messageDto.setSentAt(LocalDateTime.now());

        // DB 저장 및 메시지 브로드캐스트
        chatRoomInOutService.saveMessage(messageDto);

        messagingTemplate.convertAndSend(
                "/topic/chatroom/" + messageDto.getRoomId(),
                messageDto
        );
    }

    // 방 입장
    @MessageMapping("/chat.join")
    public void joinRoom(@Header("roomId") Long roomId,
                         @Header("X-User-Id") Long userId,
                         @Header("X-User-Nickname") String nickname,
                         @Header(value = "roomCode", required = false) Integer roomCode) {

        ChatMessageDto joinMessage = chatRoomInOutService.joinRoom(roomId, userId, nickname, roomCode);
        messagingTemplate.convertAndSend("/topic/chatroom/" + roomId, joinMessage);
    }

    // 방 퇴장
    @MessageMapping("/chat.leave")
    public void leaveRoom(@Header("roomId") Long roomId,
                          @Header("X-User-Id") Long userId,
                          @Header("X-User-Nickname") String nickname) {

        ChatMessageDto leaveMessage = chatRoomInOutService.leaveRoom(roomId, userId, nickname);
        messagingTemplate.convertAndSend("/topic/chatroom/" + roomId, leaveMessage);
    }
}
