package com.linkee.linkeechatservice.chat.command.domain.repository;


import com.linkee.linkeechatservice.chat.chat_command.chat_domain.entity.ChatRoom;
import com.linkee.linkeechatservice.chat.command.domain.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {
    Collection<ChatMessage> findByChatRoomOrderBySentAtAsc(ChatRoom room);
}
