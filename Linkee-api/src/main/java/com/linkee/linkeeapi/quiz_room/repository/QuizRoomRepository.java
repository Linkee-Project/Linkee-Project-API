package com.linkee.linkeeapi.quiz_room.repository;

import com.linkee.linkeeapi.quiz.model.entity.QuizRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRoomRepository extends JpaRepository<QuizRoom, Long> {
}
