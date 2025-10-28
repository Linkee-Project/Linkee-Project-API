package com.linkee.linkeeapi.quiz.model.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "tb_room_question")
public class RoomQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_question_id")
    private Long roomQuestionId;

    @ManyToOne
    @JoinColumn(name = "quiz_room_id", nullable = false)
    private QuizRoom quizRoom;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "quiz_order", nullable = false)
    private Integer quizOrder;

}
