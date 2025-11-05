package com.linkee.linkeeapi.chat.command.domain.repository;

import com.linkee.linkeeapi.chat.command.domain.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
