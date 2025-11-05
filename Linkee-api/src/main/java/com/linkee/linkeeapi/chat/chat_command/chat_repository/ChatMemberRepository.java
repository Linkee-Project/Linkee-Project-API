package com.linkee.linkeeapi.chat.chat_command.chat_repository;

import com.linkee.linkeeapi.chat.chat_command.chat_domain.entity.ChatMember;
import com.linkee.linkeeapi.chat.chat_command.chat_domain.entity.ChatRoom;
import com.linkee.linkeeapi.user.command.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMemberRepository extends JpaRepository<ChatMember,Long> {
    Optional<ChatMember> findByChatRoomAndUser(ChatRoom chatRoom, User user);
    List<ChatMember> findByChatRoomAndLeftAtIsNull(ChatRoom room);

    long countByChatRoom(ChatRoom chatRoom);
    List<ChatMember> findByChatRoom(ChatRoom chatRoom);
    boolean existsByChatRoomAndUser(ChatRoom chatRoom, User user);






}
