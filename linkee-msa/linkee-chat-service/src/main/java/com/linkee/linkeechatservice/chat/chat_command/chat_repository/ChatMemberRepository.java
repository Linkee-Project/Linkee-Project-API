package com.linkee.linkeechatservice.chat.chat_command.chat_repository;


import com.linkee.linkeechatservice.chat.chat_command.chat_domain.entity.ChatMember;
import com.linkee.linkeechatservice.chat.chat_command.chat_domain.entity.ChatRoom;
import com.linkee.linkeechatservice.user.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatMemberRepository extends JpaRepository<ChatMember,Long> {
    Optional<ChatMember> findByChatRoomAndUserId(ChatRoom chatRoom, User user);

    @Query("SELECT m FROM ChatMember m JOIN FETCH m.userId WHERE m.chatRoom = :room AND m.leftAt IS NULL")
    List<ChatMember> findByChatRoomAndLeftAtIsNull(@Param("room") ChatRoom room);

    long countByChatRoom(ChatRoom chatRoom);
    List<ChatMember> findByChatRoom(ChatRoom chatRoom);
    Optional<ChatMember> findByChatRoomAndUserId(ChatRoom chatRoom, Long userId);

    Optional<ChatMember> findByChatRoom_ChatRoomIdAndUserId(ChatRoom chatRoom, Long userId);



}
