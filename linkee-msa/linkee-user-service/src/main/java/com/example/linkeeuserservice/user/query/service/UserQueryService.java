package com.example.linkeeuserservice.user.query.service;


import com.example.linkeeuserservice.common.model.PageResponse;
import com.example.linkeeuserservice.user.query.dto.request.UserSearchRequest;
import com.example.linkeeuserservice.user.query.dto.response.UserListResponse;
import com.example.linkeeuserservice.user.query.dto.response.UserMeResponse;

public interface UserQueryService {

    PageResponse<UserListResponse> selectAllUsers(UserSearchRequest request);
    UserMeResponse getUserMe(String userEmail);
}
