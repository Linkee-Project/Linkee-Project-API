package com.linkee.linkeeapi.chat.command.domain.repository;

import com.linkee.linkeeapi.chat.command.domain.entity.ChatMember;
import com.linkee.linkeeapi.chat.command.domain.entity.ChatRoom;
import com.linkee.linkeeapi.user.command.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMemberRepository extends JpaRepository<ChatMember,Long> {
    Optional<ChatMember> findByChatRoomAndUser(ChatRoom chatRoom, User user);
    long countByChatRoom(ChatRoom chatRoom);
    List<ChatMember> findAllByChatRoom(ChatRoom chatRoom);

    boolean existsByChatRoomAndUser(ChatRoom foundChatRoom, User foundUser);
}
