package com.linkee.linkeeapi.chat.command.domain.repository;

import com.linkee.linkeeapi.chat.command.domain.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {

}
