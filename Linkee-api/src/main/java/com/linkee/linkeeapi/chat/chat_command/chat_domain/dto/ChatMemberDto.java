package com.linkee.linkeeapi.chat.chat_command.chat_domain.dto;

import java.time.LocalDateTime;

public record ChatMemberDto(
        Long chatMemberId,
        Long userId,
        String userNickname,
        LocalDateTime joinedAt,
        LocalDateTime leftAt
) {}
