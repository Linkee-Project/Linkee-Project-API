package com.linkee.linkeeapi.room_member.command.infrastructure.repository;

import com.linkee.linkeeapi.quiz_room.command.domain.aggregate.QuizRoom;
import com.linkee.linkeeapi.room_member.command.domain.aggregate.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    // 특정 QuizRoom에 속한 모든 RoomMember를 조회하는 메서드
    List<RoomMember> findByQuizRoom(QuizRoom quizRoom);
}