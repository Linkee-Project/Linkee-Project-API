package com.linkee.linkeeapi.chat.chat_command.chat_service;

import com.linkee.linkeeapi.chat.chat_command.chat_domain.dto.ChatMessageDto;
import com.linkee.linkeeapi.chat.chat_command.chat_domain.entity.ChatMember;
import com.linkee.linkeeapi.chat.chat_command.chat_domain.entity.ChatRoom;
import com.linkee.linkeeapi.chat.chat_command.chat_repository.ChatMemberRepository;
import com.linkee.linkeeapi.chat.chat_command.chat_repository.ChatRoomRepository;
import com.linkee.linkeeapi.common.exception.BusinessException;
import com.linkee.linkeeapi.common.exception.ErrorCode;
import com.linkee.linkeeapi.common.security.jwt.JwtTokenProvider;
import com.linkee.linkeeapi.user.command.domain.entity.User;
import com.linkee.linkeeapi.user.command.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomInOutService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private User getUserFromToken(String token) {
        if (token.startsWith("Bearer ")) token = token.substring(7);

        if (!jwtTokenProvider.validateToken(token)) {
            throw new BusinessException(ErrorCode.REPORT_NO_ACCESS, "로그인 정보 없음");
        }

        String email = jwtTokenProvider.getUsername(token);
        return userRepository.findByUserEmail(email).orElseThrow(() -> new BusinessException(ErrorCode.INVALID_USER_ID));
    }

    @Transactional
    public ChatMessageDto joinRoom(Long roomId, String token, Integer inputRoomCode) {
        User user = getUserFromToken(token);
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // 비밀방 검증
        if (room.getRoomCode() != null) {
            if (inputRoomCode == null || !room.getRoomCode().equals(inputRoomCode)) {
                throw new BusinessException(ErrorCode.INVALID_ROOM_CODE); // 틀린 비밀번호
            }
        }

        boolean alreadyJoined = chatMemberRepository.existsByChatRoomAndUser(room, user);
        if (alreadyJoined) {
            ChatMember cm = chatMemberRepository.findByChatRoomAndUser(room, user).orElseThrow();
            cm.setJoinedAt(LocalDateTime.now());
            cm.modifyIsRead();
        } else {
            ChatMember newMember = ChatMember.builder().chatRoom(room).user(user).build();
            chatMemberRepository.save(newMember);
            room.increaseJoinedCount();
            chatRoomRepository.save(room);
        }

        return ChatMessageDto.builder()
                .roomId(roomId)
                .message(user.getUserNickname() + "님이 입장했습니다.")
                .senderNickname("SYSTEM")
                .sentAt(LocalDateTime.now())
                .build();
    }

    @Transactional
    public ChatMessageDto leaveRoom(Long roomId, String token) {
        User user = getUserFromToken(token);
        ChatRoom room = chatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));

        boolean isOwner = room.getRoomOwner().getUserId().equals(user.getUserId());

        if (isOwner) {
            List<ChatMember> members = chatMemberRepository.findByChatRoom(room);
            members.forEach(ChatMember::modifyLeftAt);
            chatMemberRepository.deleteAll(members);
            chatRoomRepository.deleteById(roomId);
        } else {
            ChatMember member = chatMemberRepository.findByChatRoomAndUser(room, user).orElseThrow();
            chatMemberRepository.delete(member);
            room.decreaseJoinedCount();
            if (room.getJoinedCount() == 0) chatRoomRepository.deleteById(roomId);
            else chatRoomRepository.save(room);
        }

        return ChatMessageDto.builder()
                .roomId(roomId)
                .message(user.getUserNickname() + "님이 퇴장했습니다.")
                .senderNickname("SYSTEM")
                .sentAt(LocalDateTime.now())
                .build();
    }
}
