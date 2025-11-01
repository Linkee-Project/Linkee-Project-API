package com.linkee.linkeeapi.user.query.mapper;

import com.linkee.linkeeapi.chat_member.query.dto.request.ChatMemberSearchRequest;
import com.linkee.linkeeapi.chat_member.query.dto.response.ChatMemberResponse;
import com.linkee.linkeeapi.user.query.dto.request.UserSearchRequest;
import com.linkee.linkeeapi.user.query.dto.response.UserListResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<UserListResponse> selectAllUser(UserSearchRequest request);

    int countUser(UserSearchRequest requestMapper);

}
