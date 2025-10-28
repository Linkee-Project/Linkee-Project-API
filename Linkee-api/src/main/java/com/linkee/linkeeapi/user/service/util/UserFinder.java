package com.linkee.linkeeapi.user.service.util;

import com.linkee.linkeeapi.user.model.entity.User;
import com.linkee.linkeeapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFinder {
    private final UserRepository userRepository;

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("회원 안찾아짐"));
    }


}
