package com.linkee.linkeeapi.user.command.application.service;

import com.linkee.linkeeapi.user.command.application.dto.request.UpdateUserNickNameRequest;
import com.linkee.linkeeapi.user.command.application.dto.request.UserCreateRequest;
import com.linkee.linkeeapi.user.command.domain.entity.User;
import com.linkee.linkeeapi.user.command.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService{

    private final UserRepository userRepository;

    @Override
    public void createUser(UserCreateRequest request) {
        User user = User.builder()
                .userEmail(request.getUserEmail())
                .userPassword(request.getUserPassword()) // 보통은 암호화 필요
                .userNickname(request.getUserNickname())
                .build();

        userRepository.save(user);
    }


    @Transactional
    @Override
    public void updateNickname(UpdateUserNickNameRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.modifyUserNickName(request.getNickName());
    }


    @Transactional
    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.deactivateUser();
    }


}
