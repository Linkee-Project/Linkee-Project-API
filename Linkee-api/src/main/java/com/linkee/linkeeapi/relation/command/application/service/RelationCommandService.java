package com.linkee.linkeeapi.relation.command.application.service;

import com.linkee.linkeeapi.common.exception.BusinessException;
import com.linkee.linkeeapi.common.exception.ErrorCode;
import com.linkee.linkeeapi.relation.command.application.dto.request.RelationCreateRequest;
import com.linkee.linkeeapi.relation.command.domain.aggregate.entity.Relation;
import com.linkee.linkeeapi.relation.command.domain.aggregate.entity.RelationStatus;
import com.linkee.linkeeapi.relation.command.infrastructure.repository.RelationRepository;
import com.linkee.linkeeapi.user.command.domain.entity.User;
import com.linkee.linkeeapi.user.command.infrastructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RelationCommandService {

    private final RelationRepository relationRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createRelation(RelationCreateRequest request) {

        User requester = userRepository.findById(request.getRequesterId())
                .orElseThrow(() -> new IllegalArgumentException("잘못 된 사용자입니다"));
        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new IllegalArgumentException("잘못 된 사용자입니다"));

        // 두 사용자 간의 관계가 이미 존재하는지 확인
        Optional<Relation> optionalRelation = relationRepository.findByUsers(requester, receiver);

        if (optionalRelation.isPresent()) {
            // 관계가 이미 존재하면 , 재활용
            Relation existingRelation = optionalRelation.get();

            switch (existingRelation.getRelationStatus()) {
                case A:
                    throw new BusinessException(ErrorCode.RELATION_ALREADY_EXISTS);
                case P:
                    throw new BusinessException(ErrorCode.RELATION_REQUEST_ALREADY_EXISTS);
                case R:
                    // 거절된 상태에서 다시 요청하는 경우, 기존 데이터를 업데이트
                    existingRelation.reRequest(requester, receiver);
                    relationRepository.save(existingRelation);
                    break;
            }
        } else {
            Relation relation = Relation.builder()
                    .requester(requester)
                    .receiver(receiver)
                    .build();

            relationRepository.save(relation);
        }
    }


    @Transactional
    public void updateRelationStatus(Long relationId, RelationStatus status) {
        Relation relation = relationRepository.findById(relationId)
                .orElseThrow(() -> new IllegalArgumentException("잘못 된 사용자입니다"));

        relation.modifyRelationStatus(status);

    }

    @Transactional
    public void deleteRelation(Long relationId) {
        Relation relation = relationRepository.findById(relationId)
                .orElseThrow(() -> new IllegalArgumentException("잘못 된 사용자입니다"));

        relationRepository.delete(relation);
    }
}