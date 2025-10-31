package com.linkee.linkeeapi.quiz.model.entity;

import com.linkee.linkeeapi.question.query.model.entity.Question;
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
    @JoinColumn(name = "quiz_room_id", nullable = false, foreignKey = @ForeignKey(name = "FK_room_question_quizroom"))
    private QuizRoom quizRoom;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false, foreignKey = @ForeignKey(name = "FK_room_question_question"))
    private Question question;

    @Column(name = "quiz_order", nullable = false)
    private Integer quizOrder;

}
