package com.linkee.linkeeapi.comment.command.application.service;

import com.linkee.linkeeapi.comment.command.application.dto.request.CreateCommentRequestDto;
import com.linkee.linkeeapi.comment.command.application.dto.request.UpdateCommentRequestDto;
import com.linkee.linkeeapi.comment.command.application.dto.response.CreateCommentResponseDto;
import com.linkee.linkeeapi.comment.command.application.dto.response.UpdateCommentResponseDto;
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

    //댓글 등록
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

    //댓글 수정
    public UpdateCommentResponseDto updateComment(
            Long questionId, Long commentId, Long userId, UpdateCommentRequestDto request
    ) {
        Comment comment = jpaCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        if (!comment.getQuestion().getQuestionId().equals(questionId)) {
            throw new IllegalArgumentException("해당 질문의 댓글이 아닙니다.");
        }
        if (!comment.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("댓글 작성자만 수정할 수 있습니다.");
        }

        comment.updateContent(request.getCommentContent());

        return UpdateCommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .commentContent(comment.getCommentContent())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }


}