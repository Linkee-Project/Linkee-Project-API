package com.linkee.linkeechatservice.qna.command.application.service;


import com.linkee.linkeechatservice.chat.chat_command.chat_domain.entity.ChatMember;
import com.linkee.linkeechatservice.chat.chat_command.chat_domain.entity.ChatRoom;
import com.linkee.linkeechatservice.chat.chat_command.chat_repository.ChatMemberRepository;
import com.linkee.linkeechatservice.chat.chat_command.chat_repository.ChatRoomRepository;
import com.linkee.linkeechatservice.qna.command.application.dto.request.CreateQnaRequestDto;
import com.linkee.linkeechatservice.qna.command.domain.aggregate.Qna;
import com.linkee.linkeechatservice.qna.command.infrastructure.repository.JpaQnaRepository;
import com.linkee.linkeechatservice.user.util.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QnaCommandServiceImpl implements QnaCommandService{

    private final UserFinder userFinder;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final JpaQnaRepository qnaRepository;

    //문제 답 생성
    public void createQna(CreateQnaRequestDto request, Long userId) {
        ChatRoom chatRoom = chatRoomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));

        ChatMember chatMember = chatMemberRepository.findByChatRoomAndUserId(chatRoom, userId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방에 속한 멤버가 아닙니다."));

        Qna qna = Qna.builder()
                .qnaQuestion(request.getQuestion())
                .qnaAnswer(request.getAnswer())
                .chatRoom(chatRoom)
                .chatMember(chatMember)
                .build();

        qnaRepository.save(qna);
    }

}
