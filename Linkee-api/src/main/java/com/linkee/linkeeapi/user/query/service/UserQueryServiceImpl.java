package com.linkee.linkeeapi.user.query.service;

import com.linkee.linkeeapi.auth.authService.AuthService;
import com.linkee.linkeeapi.common.enums.Status;
import com.linkee.linkeeapi.common.model.PageResponse;
import com.linkee.linkeeapi.user.command.domain.entity.User;
import com.linkee.linkeeapi.user.command.infrastructure.repository.UserRepository;
import com.linkee.linkeeapi.user.query.dto.request.UserSearchRequest;
import com.linkee.linkeeapi.user.query.dto.response.UserListResponse;
import com.linkee.linkeeapi.user.query.dto.response.UserMeResponse;
import com.linkee.linkeeapi.user.query.mapper.UserMapper;
import com.linkee.linkeeapi.user_grade.command.infrastructure.repository.UserGradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService{


    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final AuthService authService;

    @Override
    public PageResponse<UserListResponse> selectAllUsers(UserSearchRequest request) {

        int page = request.getPage() != null && request.getPage() > 0 ? request.getPage() : 0;
        int size = request.getSize() != null && request.getSize() > 0 ? request.getSize() : 10;
        int offset = page * size;

        // Mapper에 전달할 record 생성 (offset 포함)
        UserSearchRequest requestMapper = UserSearchRequest.builder()
                .userId(request.getUserId())
                .userNickname(request.getUserNickname())
                .userStatus(request.getUserStatus())
                .userRole(request.getUserRole())
                .keyword(request.getKeyword())
                .page(page)
                .size(size)
                .offset(offset)
                .build();


        List<UserListResponse> results = userMapper.selectAllUser(requestMapper);

        int total = userMapper.countUser(requestMapper);


        return PageResponse.from(results, page, size, total);

    }



    // id 값으로 한건조회(자기정보)
    @Override
    public UserMeResponse getUserMe(String userEmail) {

        User foundUser = userRepository.findByUserEmail(userEmail).orElseThrow();

        if(foundUser.getUserStatus() == Status.N){
            throw new IllegalArgumentException("비활성화 된 회원입니다");
        }

        System.out.println("service에서 호출 :" + authService.getCurrentUsername());
        return userMapper.selectUserMe(userEmail);
    }


}
