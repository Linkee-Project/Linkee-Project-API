package com.example.linkeeuserservice.user.command.application.service;


import com.example.linkeeuserservice.common.enums.Status;
import com.example.linkeeuserservice.common.exception.BusinessException;
import com.example.linkeeuserservice.common.exception.ErrorCode;
import com.example.linkeeuserservice.relation.command.infrastructure.repository.RelationRepository;

import com.example.linkeeuserservice.user.command.domain.entity.User;
import com.example.linkeeuserservice.user.command.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService{

    private final UserRepository userRepository;
    private final RelationRepository relationRepository;


    @Transactional
    @Override
    public void updateNickname(Long userId ,String newNickName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_USER_ID));

        if(user.getUserStatus() == Status.N){
            throw new BusinessException(ErrorCode.INVALID_USER_ID,"탈퇴한 회원입니다.");
        }

        user.modifyUserNickName(newNickName);
    }


    @Transactional
    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_USER_ID));

        user.deactivateUser();

        // relation에 receiver 나 requester 정보있으면 삭제
        relationRepository.deleteByReceiverOrRequester(user,user);

    }


}
