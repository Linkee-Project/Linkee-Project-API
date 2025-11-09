package com.linkee.linkeeapi.user.query.controller;

import com.linkee.linkeeapi.common.model.PageResponse;
import com.linkee.linkeeapi.common.security.model.CustomUser;
import com.linkee.linkeeapi.user.command.domain.entity.User;
import com.linkee.linkeeapi.user.command.infrastructure.repository.UserRepository;
import com.linkee.linkeeapi.user.query.dto.request.UserSearchRequest;
import com.linkee.linkeeapi.user.query.dto.response.UserListResponse;
import com.linkee.linkeeapi.user.query.dto.response.UserMeResponse;
import com.linkee.linkeeapi.user.query.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserQueryController {

    private final UserQueryService userQueryService;
    private final UserRepository userRepository;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public PageResponse<UserListResponse> selectAllUsers(UserSearchRequest request){

        return userQueryService.selectAllUsers(request);
    }

    @GetMapping("/byId/{userId}")
    public ResponseEntity<User> selectByUserId(Long userId){

       User user = userRepository.findById(userId).orElseThrow();

        return ResponseEntity.ok(user);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public ResponseEntity<UserMeResponse> getUserMe(@AuthenticationPrincipal CustomUser customUser) {
        System.out.println("여기로오긴하냐?");
        if (customUser == null) {
            // Gateway에서 헤더가 제대로 안오면 null
            return ResponseEntity.status(401).build();
        }

        // Core-Service에서는 더 이상 토큰 검증 필요 없음
        String userEmail = customUser.getUsername(); // 또는 customUser.getEmail()
        Long userId = customUser.getUserId();

        System.out.println("CustomUser 정보: userId=" + userId + ", email=" + userEmail + ", role=" + customUser.getRole());

        UserMeResponse response = userQueryService.getUserMe(userEmail);
        return ResponseEntity.ok(response);
    }
}
