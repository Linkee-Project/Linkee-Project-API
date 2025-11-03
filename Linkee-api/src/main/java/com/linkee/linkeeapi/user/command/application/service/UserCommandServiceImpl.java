package com.linkee.linkeeapi.user.command.application.service;

import com.linkee.linkeeapi.category.command.aggregate.Category;
import com.linkee.linkeeapi.category.command.infrastructure.repository.CategoryRepository;
import com.linkee.linkeeapi.grade.command.domain.aggregate.entity.Grade;
import com.linkee.linkeeapi.grade.command.infrastructure.GradeRepository;
import com.linkee.linkeeapi.user.command.application.dto.request.UpdateUserNickNameRequest;
import com.linkee.linkeeapi.user.command.application.dto.request.UserCreateRequest;
import com.linkee.linkeeapi.user.command.domain.entity.User;
import com.linkee.linkeeapi.user.command.infrastructure.repository.UserRepository;
import com.linkee.linkeeapi.user_grade.command.domain.entity.UserGrade;
import com.linkee.linkeeapi.user_grade.command.infrastructure.repository.UserGradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService{

    private final UserRepository userRepository;


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
