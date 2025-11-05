package com.linkee.linkeeapi.chat.chat_command.chat_controller;

import com.linkee.linkeeapi.chat.chat_command.chat_domain.dto.ChatMemberDto;
import com.linkee.linkeeapi.chat.chat_command.chat_domain.entity.ChatMessageMongo;
import com.linkee.linkeeapi.chat.chat_command.chat_domain.entity.ChatRoom;
import com.linkee.linkeeapi.chat.chat_command.chat_service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/rooms")
    public List<ChatRoom> getAllRooms(@RequestHeader("Authorization") String token) {
        return chatService.getAllRooms(token);
    }

    @GetMapping("/rooms/{roomId}/messages")
    public List<ChatMessageMongo> getRoomMessages(@PathVariable Long roomId,
                                                  @RequestHeader("Authorization") String token) {
        return chatService.getMessages(roomId, token);
    }

    @PostMapping("/rooms/{roomId}/join")
    public ChatMemberDto joinRoom(@PathVariable Long roomId,
                                  @RequestHeader("Authorization") String token) {
        ChatMemberDto member = chatService.joinRoom(roomId, token);
        messagingTemplate.convertAndSend("/topic/room/" + roomId + "/members",
                chatService.getRoomMembers(roomId));
        return member;
    }

    @PostMapping("/rooms/{roomId}/leave")
    public void leaveRoom(@PathVariable Long roomId,
                          @RequestHeader("Authorization") String token) {
        chatService.leaveRoom(roomId, token);
        messagingTemplate.convertAndSend("/topic/room/" + roomId + "/members",
                chatService.getRoomMembers(roomId));
    }

    @PostMapping("/rooms/{roomId}/send")
    public void sendMessage(@PathVariable Long roomId,
                            @RequestHeader("Authorization") String token,
                            @RequestBody String message) {
        ChatMessageMongo msg = chatService.sendMessage(roomId, token, message);
        messagingTemplate.convertAndSend("/topic/room/" + roomId, msg);
    }
}
