package com.linkee.linkeeapi.common.security.jwt;

import com.linkee.linkeeapi.chat.chat_command.chat_service.ChatService;
import com.linkee.linkeeapi.user.command.infrastructure.repository.UserRepository;
import com.linkee.linkeeapi.user.command.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompAuthInterceptor implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final ChatService chatService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (accessor.getCommand() == StompCommand.CONNECT) {
            var nativeHeaders = accessor.getNativeHeader("Authorization");
            if (nativeHeaders == null || nativeHeaders.isEmpty()) {
                throw new RuntimeException("JWT token missing");
            }

            String token = nativeHeaders.get(0); // Bearer 없이 토큰 전체 사용
            String email = jwtTokenProvider.getUsername(token);
            if (email == null) throw new RuntimeException("Invalid JWT token");

            User user = userRepository.findByUserEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            accessor.getSessionAttributes().put("nickname", user.getUserNickname());
            accessor.getSessionAttributes().put("userId", user.getUserId());

            // 선택된 방(roomId)이 있으면 ChatMember 생성
            var roomIdHeader = accessor.getFirstNativeHeader("roomId");
            if (roomIdHeader != null) {
                Long roomId = Long.valueOf(roomIdHeader);
                chatService.joinRoom(roomId, token);
            }
        }

        return message;
    }
}
