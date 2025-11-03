package com.linkee.linkeeapi.comment.command.application.service;

import com.linkee.linkeeapi.comment.command.application.dto.request.CreateCommentRequestDto;
import com.linkee.linkeeapi.comment.command.application.dto.response.CreateCommentResponseDto;
import com.linkee.linkeeapi.comment.command.domain.aggregate.Comment;
import com.linkee.linkeeapi.comment.command.infrastructure.repository.JpaCommentRepository;
import com.linkee.linkeeapi.question.command.domain.aggregate.Question;
import com.linkee.linkeeapi.question.command.infrastructure.repository.JpaQuestionRepository;
import com.linkee.linkeeapi.user.command.application.service.util.UserFinder;
import com.linkee.linkeeapi.user.command.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentCommandService {

    private final JpaCommentRepository jpaCommentRepository;
    private final JpaQuestionRepository jpaQuestionRepository;
    private final UserFinder userFinder;

    public CreateCommentResponseDto createComment(Long questionId, Long userId, CreateCommentRequestDto req) {
        Question question = jpaQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문제입니다."));
        User user = userFinder.getById(userId);

        Comment saved;
        if (req.getParentCommentId() == null) {
            Comment root = Comment.createRoot(question, user, req.getContent());
            saved = jpaCommentRepository.save(root);
        } else {
            Comment parent = jpaCommentRepository.findById(req.getParentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다."));
            Comment reply = Comment.createReply(question, user, parent, req.getContent());
            saved = jpaCommentRepository.save(reply);
        }

        return new CreateCommentResponseDto(saved.getCommentId());
    }
}