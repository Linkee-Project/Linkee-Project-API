package com.linkee.linkeeapi.user.query.controller;

import com.linkee.linkeeapi.common.model.PageResponse;
import com.linkee.linkeeapi.user.query.dto.request.UserSearchRequest;
import com.linkee.linkeeapi.user.query.dto.response.UserListResponse;
import com.linkee.linkeeapi.user.query.dto.response.UserMeResponse;
import com.linkee.linkeeapi.user.query.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserQueryController {

    private final UserQueryService userQueryService;


    @GetMapping
    public PageResponse<UserListResponse> selectAllUsers(UserSearchRequest request){

        return userQueryService.selectAllUsers(request);
    }


    @GetMapping("/me/{userId}")
    public ResponseEntity<UserMeResponse> getUserMe(@PathVariable Long userId) {
        UserMeResponse response = userQueryService.getUserMe(userId);

        return ResponseEntity.ok(response);
    }

}
