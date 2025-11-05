package com.linkee.linkeeapi.chat.command.domain.repository;

import com.linkee.linkeeapi.chat.command.domain.entity.ChatMessageMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface ChatMessageMongoRepository extends MongoRepository<ChatMessageMongo, String> {
    List<ChatMessageMongo> findByRoomIdOrderBySentAtAsc(Long roomId);

}
