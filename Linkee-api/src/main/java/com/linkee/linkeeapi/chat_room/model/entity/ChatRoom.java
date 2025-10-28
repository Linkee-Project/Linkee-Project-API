package com.linkee.linkeeapi.chat_room.model.entity;

import com.linkee.linkeeapi.common.enums.Status;
import com.linkee.linkeeapi.common.model.BaseTimeEntity;
import com.linkee.linkeeapi.user.model.entity.User;
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
    @Column(name = "is_group", nullable = false, columnDefinition = "ENUM('Y','N') DEFAULT 'N'" )
    private Status isGroup = Status.N;

    @Enumerated(EnumType.STRING)
    @Column(name = "chat_room_type", nullable = false, columnDefinition = "ENUM('GAME','CHAT')")
    private ChatRoomType chatRoomType;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_private", nullable = false, columnDefinition = "ENUM('Y','N') DEFAULT 'Y'")
    private Status isPrivate = Status.N;

    @Column(name = "room_code")
    private int roomCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_owner", nullable = false, foreignKey = @ForeignKey(name = "FK_chatroom_owner"))
    private User roomOwner;

    @Column(name = "joined_count", nullable = false)
    private Integer joinedCount;

    @Column(name = "room_capacity")
    private Integer roomCapacity;

    public enum ChatRoomType {
        GAME,
        CHAT
    }


}
