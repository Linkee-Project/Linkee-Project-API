package com.linkee.linkeechatservice.chat.chat_command.chat_service;

import com.linkee.linkeechatservice.chat.chat_command.chat_domain.dto.ChatMessageDto;
import com.linkee.linkeechatservice.chat.chat_command.chat_domain.entity.ChatMember;
import com.linkee.linkeechatservice.chat.chat_command.chat_domain.entity.ChatMessageMongo;
import com.linkee.linkeechatservice.chat.chat_command.chat_domain.entity.ChatRoom;
import com.linkee.linkeechatservice.chat.chat_command.chat_repository.ChatMemberRepository;
import com.linkee.linkeechatservice.chat.chat_command.chat_repository.ChatMessageMongoRepository;
import com.linkee.linkeechatservice.chat.chat_command.chat_repository.ChatRoomRepository;
import com.linkee.linkeechatservice.common.enums.Status;
import com.linkee.linkeechatservice.common.exception.BusinessException;
import com.linkee.linkeechatservice.common.exception.ErrorCode;

import com.linkee.linkeechatservice.common.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomInOutService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageMongoRepository  chatMessageMongoRepository;

    // JWT에서 유저 정보 추출
    private Claims getClaimsFromToken(String token) {
        if (token.startsWith("Bearer ")) token = token.substring(7);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new BusinessException(ErrorCode.REPORT_NO_ACCESS, "로그인 정보 없음");
        }
        return jwtTokenProvider.getClaims(token);
    }

    @Transactional
    public ChatMessageDto joinRoom(Long roomId, Long userId, String nickname, Integer inputRoomCode) {
        // 방 조회
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // 비밀방 검증
        if (room.getIsPrivate() == Status.Y && !room.getRoomCode().equals(inputRoomCode)) {
            return ChatMessageDto.builder()
                    .roomId(roomId)
                    .senderNickname("SYSTEM")
                    .message("비밀번호가 틀렸습니다.")
                    .sentAt(LocalDateTime.now())
                    .build();
        }

        // 참여자 등록
        chatMemberRepository.findByChatRoomAndUserId(room, userId)
                .orElseGet(() -> {
                    ChatMember newMember = ChatMember.builder()
                            .chatRoom(room)
                            .userId(userId)
                            .userNickname(nickname)
                            .build();
                    chatMemberRepository.save(newMember);
                    room.increaseJoinedCount();
                    chatRoomRepository.save(room);
                    return newMember;
                });

        return ChatMessageDto.builder()
                .roomId(roomId)
                .senderNickname("SYSTEM")
                .message(nickname + "님이 입장했습니다.")
                .sentAt(LocalDateTime.now())
                .build();
    }

    @Transactional
    public ChatMessageDto leaveRoom(Long roomId, Long userId, String nickname) {

        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        ChatMember member = chatMemberRepository.findByChatRoomAndUserId(room, userId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        chatMemberRepository.delete(member);



        room.decreaseJoinedCount();

        if (room.getJoinedCount() == 0) chatRoomRepository.deleteById(roomId);
        else chatRoomRepository.save(room);

        return ChatMessageDto.builder()
                .roomId(roomId)
                .senderNickname("SYSTEM")
                .message(nickname + "님이 퇴장했습니다.")
                .sentAt(LocalDateTime.now())
                .build();
    }

    // 메시지 저장
    public void saveMessage(ChatMessageDto messageDto) {
        chatMessageMongoRepository.save(ChatMessageMongo.builder()
                .roomId(messageDto.getRoomId())
                .senderId(messageDto.getSenderId())
                .senderNickname(messageDto.getSenderNickname())
                .message(messageDto.getMessage())
                .sentAt(messageDto.getSentAt())
                .build());
    }

}
