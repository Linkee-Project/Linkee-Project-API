package com.linkee.linkeeapi.user.query.controller;

import com.linkee.linkeeapi.common.model.PageResponse;
import com.linkee.linkeeapi.user.query.dto.request.UserSearchRequest;
import com.linkee.linkeeapi.user.query.dto.response.UserListResponse;
import com.linkee.linkeeapi.user.query.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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

}
