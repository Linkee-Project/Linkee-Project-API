package com.linkee.linkeechatservice.chat.chat_command.chat_domain.entity;

import com.linkee.linkeechatservice.common.enums.Status;
import com.linkee.linkeechatservice.common.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_chat_room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long chatRoomId;

    @Column(name = "chat_room_name", nullable = false, length = 50)
    private String chatRoomName;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "chat_room_type", nullable = false, columnDefinition = "ENUM('GAME','CHAT')")
    private ChatRoomType chatRoomType = ChatRoomType.CHAT;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "is_private", nullable = false, columnDefinition = "ENUM('Y','N') DEFAULT 'Y'")
    private Status isPrivate = Status.N;

    @Column(name = "room_code")
    private Integer roomCode;

    // User 엔티티 대신 owner id, nickname만 저장
    @Column(name = "room_owner_id", nullable = false)
    private Long roomOwnerId;

    @Column(name = "room_owner_nickname", nullable = false)
    private String roomOwnerNickname;

    @Column(name = "joined_count", nullable = false)
    @Builder.Default
    private Integer joinedCount = 1;

    @Column(name = "room_capacity")
    private Integer roomCapacity;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "room_status", nullable = false, columnDefinition = "ENUM('Y','N') DEFAULT 'Y'" )
    private Status roomStatus = Status.Y;

    public void decreaseJoinedCount() {
        this.joinedCount = this.joinedCount - 1;
        if (this.joinedCount <= 0) {
            this.roomStatus = Status.N;
        }
    }

    public void increaseJoinedCount() {
        this.joinedCount++;
    }

    public void closeRoom() {
        this.roomStatus = Status.N;
    }
}
