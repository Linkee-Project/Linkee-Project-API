package com.linkee.linkeechatservice.user.util;


import com.linkee.linkeechatservice.common.exception.BusinessException;
import com.linkee.linkeechatservice.common.exception.ErrorCode;
import com.linkee.linkeechatservice.user.entity.User;
import com.linkee.linkeechatservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFinder {
    private final UserRepository userRepository;

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.INVALID_USER_ID));
    }

    // ✅ 이메일 기반 조회 추가
    public User getByEmail(String email) {
        return userRepository.findByUserEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_USER_ID));
    }



}
