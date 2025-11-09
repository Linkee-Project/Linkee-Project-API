package com.linkee.linkeechatservice.chat.command.application.service.command_service;


import com.linkee.linkeechatservice.chat.chat_command.chat_domain.entity.ChatMember;
import com.linkee.linkeechatservice.chat.chat_command.chat_domain.entity.ChatRoom;
import com.linkee.linkeechatservice.chat.chat_command.chat_repository.ChatMemberRepository;
import com.linkee.linkeechatservice.chat.chat_command.chat_repository.ChatRoomRepository;
import com.linkee.linkeechatservice.chat.command.domain.dto.command_dto.request.ChatMessageCreateRequest;
import com.linkee.linkeechatservice.chat.command.domain.dto.command_dto.request.MarkAsReadRequest;
import com.linkee.linkeechatservice.chat.command.domain.entity.ChatMessage;
import com.linkee.linkeechatservice.chat.command.domain.repository.ChatMessageRepository;
import com.linkee.linkeechatservice.common.enums.Status;
import com.linkee.linkeechatservice.user.entity.User;
import com.linkee.linkeechatservice.user.util.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageCommandServiceImpl implements ChatMessageCommandService{

    private final ChatMessageRepository chatMessageRepository;
    private final UserFinder userFinder;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;

    /*
     * 메시지 전송 시 호출
     * 모든 멤버의 isRead = N으로 초기화 (보낸 사람 제외)
     */

    @Override
    public void createChatMessage(ChatMessageCreateRequest request) {

        User foundUser = userFinder.getById(request.getSenderId());
        ChatRoom foundRoom = chatRoomRepository.findById(request.getChatRoomId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));

        ChatMessage message = ChatMessage.builder()
                .messageContent(request.getMessageContent())
                .chatRoom(foundRoom)
                .sender(foundUser)
                .build();

        chatMessageRepository.save(message);

        //모든 멤버의 isRead=N //새로운 메세지가 등록되었으므로
        List<ChatMember> members = chatMemberRepository.findByChatRoom(foundRoom);
        for (ChatMember member : members) {
            if(!member.getUserId().equals(request.getSenderId())) {
                member.setIsRead(Status.N);
                chatMemberRepository.save(member);
            }
        }
    }

    /*
     * 사용자가 채팅방 열 때 호출
     * 해당 멤버의 isRead = Y 처리
     */
    @Override
    public void markChatRoomAsRead(MarkAsReadRequest request) {
        ChatRoom chatRoom = chatRoomRepository.findById(request.getChatRoomId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방"));

        User user = userFinder.getById(request.getUserId());

        ChatMember chatMember = chatMemberRepository.findByChatRoomAndUserId(chatRoom, user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자는 채팅방 멤버가 아닙니다."));

        chatMember.modifyIsRead();
        chatMemberRepository.save(chatMember);
    }
}
