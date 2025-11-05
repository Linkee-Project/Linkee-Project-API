package com.linkee.linkeeapi.chat_message.command.domain.aggregate.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat_messages") // MongoDB 컬렉션 이름
public class ChatMessageMongo {

    @Id
    private String id;

    private Long roomId;             // 채팅방 ID
    private Long senderId;           // 보낸 사람 ID
    private String senderNickname;   // 보낸 사람 닉네임
    private String messageContent;   // 메시지 내용
    private LocalDateTime sentAt;    // 전송 시각
}
