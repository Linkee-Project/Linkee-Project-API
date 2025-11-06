package com.linkee.linkeeapi.chat.chat_command.chat_service;

import com.linkee.linkeeapi.chat.chat_command.chat_domain.dto.ChatMessageDto;
import com.linkee.linkeeapi.chat.chat_command.chat_domain.entity.ChatMember;
import com.linkee.linkeeapi.chat.chat_command.chat_domain.entity.ChatRoom;
import com.linkee.linkeeapi.chat.chat_command.chat_domain.entity.ChatRoomType;
import com.linkee.linkeeapi.chat.chat_command.chat_repository.ChatMemberRepository;
import com.linkee.linkeeapi.chat.chat_command.chat_repository.ChatRoomRepository;
import com.linkee.linkeeapi.common.exception.BusinessException;
import com.linkee.linkeeapi.common.exception.ErrorCode;
import com.linkee.linkeeapi.common.security.jwt.JwtTokenProvider;
import com.linkee.linkeeapi.user.command.domain.entity.User;
import com.linkee.linkeeapi.user.command.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatRoomInOutService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // ✅ 토큰으로 유저 확인
    private User getUserFromToken(String token) {
        // "Bearer "로 시작하면 제거
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (!jwtTokenProvider.validateToken(token)) {
            throw new BusinessException(ErrorCode.REPORT_NO_ACCESS,"로그인정보가 존재하지 않습니다.");
        }

        String email = jwtTokenProvider.getUsername(token);
        return userRepository.findByUserEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_USER_ID));
    } // JWT 검증용 (아래에 설명 있음)

    /**
     * 방 입장 처리
     */
    @Transactional
    public ChatMessageDto joinRoom(Long roomId, String token) {
        User user = getUserFromToken(token);
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        ChatRoomType type = room.getChatRoomType();
        boolean alreadyJoined = chatMemberRepository.existsByChatRoomAndUser(room, user);

        if (alreadyJoined) {
            // 재입장
            ChatMember cm = chatMemberRepository.findByChatRoomAndUser(room, user)
                    .orElseThrow();
            cm.setJoinedAt(LocalDateTime.now());
            cm.modifyIsRead();
        } else {
            // 신규 입장
            if (type == ChatRoomType.GAME) {
                boolean roomFull = room.getJoinedCount() >=
                        (room.getRoomCapacity() == null ? 5 : room.getRoomCapacity());
                if (roomFull) throw new BusinessException(ErrorCode.ROOM_IS_FULL);
            }

            ChatMember newMember = ChatMember.builder()
                    .chatRoom(room)
                    .user(user)
                    .build();
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

    /**
     * 방 퇴장 처리
     */
    @Transactional
    public ChatMessageDto leaveRoom(Long roomId, String token) {
        User user = getUserFromToken(token);
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.decreaseJoinedCount();
        chatRoomRepository.save(room);

        chatMemberRepository.deleteByChatRoomAndUser(room, user);

        return ChatMessageDto.builder()
                .roomId(roomId)
                .message(user.getUserNickname() + "님이 퇴장했습니다.")
                .senderNickname("SYSTEM")
                .sentAt(LocalDateTime.now())
                .build();
    }
}

