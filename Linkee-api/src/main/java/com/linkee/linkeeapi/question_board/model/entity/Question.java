package com.linkee.linkeeapi.question_board.model.entity;

import com.linkee.linkeeapi.category.model.entity.Category;
import com.linkee.linkeeapi.common.enums.Status;
import com.linkee.linkeeapi.common.model.BaseTimeEntity;
import com.linkee.linkeeapi.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_question")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "question_title",length = 100,nullable = false)
    private String questionTitle;

    @Lob
    @Column(name = "question_question", nullable = false, columnDefinition = "TEXT")
    private String questionQuestion;

    @Column(name = "question_answer", nullable = false)
    private Integer questionAnswer;

    @Enumerated(EnumType.STRING)
    @Column(name="is_qualified",  nullable = false, columnDefinition = "ENUM('Y','N') DEFAULT 'N'")
    private Status isQualified = Status.N;

    @Column(name = "question_views")
    private Long questionViews;

    @Enumerated(EnumType.STRING)
    @Column(name="is_Deleted",  nullable = false, columnDefinition = "ENUM('Y','N') DEFAULT 'N'")
    private Status isDeleted = Status.N;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "category_id", nullable = false, foreignKey = @ForeignKey(name = "FK_question_category"))
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_question_user"))
    private User user;







}