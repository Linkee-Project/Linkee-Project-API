package com.linkee.linkeeapi.room_member.command.domain.aggregate;

import com.linkee.linkeeapi.common.enums.Status;
import com.linkee.linkeeapi.quiz_room.command.domain.aggregate.QuizRoom;
import com.linkee.linkeeapi.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_room_member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(name = "FK_room_member_member_id"))
    private User member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_room_id", nullable = false, foreignKey = @ForeignKey(name = "FK_room_member_quiz_room_id"))
    private QuizRoom quizroom;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_ready", nullable = false, columnDefinition = "ENUM('Y','N') DEFAULT 'N'")
    private Status isReady;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_victory", columnDefinition = "ENUM('Y','N')")
    private Status isVictory;

    @Column(name = "joined_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime joinedAt;

    @Column(name = "lefted_at")
    private LocalDateTime leftedAt;
}
