package com.linkee.linkeeapi.chat.command.application.service.command_service;

import com.linkee.linkeeapi.chat.command.domain.entity.ChatMessageMongo;
import com.linkee.linkeeapi.chat.command.domain.repository.ChatMessageMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageMongoService {

    private final ChatMessageMongoRepository chatMessageMongoRepository;

    public ChatMessageMongo save(ChatMessageMongo message) {
        message.setSentAt(LocalDateTime.now());
        return chatMessageMongoRepository.save(message);
    }

    public List<ChatMessageMongo> getMessages(Long roomId) {
        return chatMessageMongoRepository.findByRoomIdOrderBySentAtAsc(roomId);
    }
}
