package com.linkee.linkeeapi.chat.chat_command.chat_service;

import com.linkee.linkeeapi.chat.chat_command.chat_domain.dto.ChatMemberDto;
import com.linkee.linkeeapi.chat.chat_command.chat_domain.entity.ChatMember;
import com.linkee.linkeeapi.chat.chat_command.chat_domain.entity.ChatMessageMongo;
import com.linkee.linkeeapi.chat.chat_command.chat_domain.entity.ChatRoom;
import com.linkee.linkeeapi.chat.chat_command.chat_repository.ChatMemberRepository;
import com.linkee.linkeeapi.chat.chat_command.chat_repository.ChatMessageMongoRepository;
import com.linkee.linkeeapi.chat.chat_command.chat_repository.ChatRoomRepository;
import com.linkee.linkeeapi.common.enums.Status;
import com.linkee.linkeeapi.common.security.jwt.JwtTokenProvider;
import com.linkee.linkeeapi.user.command.domain.entity.User;
import com.linkee.linkeeapi.user.command.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final ChatMessageMongoRepository chatMessageMongoRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public List<ChatRoom> getAllRooms(String token) {
        User user = getUserFromToken(token);
        return chatRoomRepository.findAll().stream()
                .filter(r -> r.getRoomStatus() == Status.Y)
                .collect(Collectors.toList());
    }

    public List<ChatMessageMongo> getMessages(Long roomId, String token) {
        getUserFromToken(token); // JWT 검증만
        return chatMessageMongoRepository.findByRoomIdOrderBySentAtAsc(roomId);
    }

    @Transactional
    public ChatMessageMongo sendMessage(Long roomId, String token, String message) {
        User user = getUserFromToken(token);
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        ChatMessageMongo msg = ChatMessageMongo.builder()
                .roomId(roomId)
                .senderId(user.getUserId())
                .senderNickname(user.getUserNickname())
                .message(message)
                .sentAt(LocalDateTime.now())
                .build();

        return chatMessageMongoRepository.save(msg);
    }

    @Transactional
    public ChatMemberDto joinRoom(Long roomId, String token) {
        User user = getUserFromToken(token);
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        ChatMember member = chatMemberRepository.findByChatRoomAndUser(room, user)
                .orElse(null);

        if (member != null) {
            member.setJoinedAt(LocalDateTime.now());
            member.setLeftAt(null);
            chatMemberRepository.save(member);
        } else {
            member = ChatMember.builder()
                    .chatRoom(room)
                    .user(user)
                    .joinedAt(LocalDateTime.now())
                    .isRead(Status.N)
                    .build();
            chatMemberRepository.save(member);
            room.increaseJoinedCount();
            chatRoomRepository.save(room);
        }

        return toDto(member);
    }

    @Transactional
    public void leaveRoom(Long roomId, String token) {
        User user = getUserFromToken(token);
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        ChatMember member = chatMemberRepository.findByChatRoomAndUser(room, user)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        member.setLeftAt(LocalDateTime.now());
        chatMemberRepository.save(member);

        room.decreaseJoinedCount();
        chatRoomRepository.save(room);
    }

    public List<ChatMemberDto> getRoomMembers(Long roomId) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        return chatMemberRepository.findByChatRoomAndLeftAtIsNull(room).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private User getUserFromToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid token");
        }
        token = token.substring(7);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new RuntimeException("Invalid token");
        }

        String email = jwtTokenProvider.getUsername(token);
        return userRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private ChatMemberDto toDto(ChatMember member) {
        return new ChatMemberDto(
                member.getChatMemberId(),
                member.getUser().getUserId(),
                member.getUser().getUserNickname(),
                member.getJoinedAt(),
                member.getLeftAt()
        );
    }
}
