package com.linkee.linkeechatservice.chat.chat_command.chat_repository;

import com.linkee.linkeechatservice.chat.chat_command.config.FeignClientConfig;
import com.linkee.linkeechatservice.common.model.dto.ApiResponse;
import com.linkee.linkeechatservice.common.security.model.CustomUser;
import com.linkee.linkeechatservice.user.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/* 1. API Gateway 호출*/
@FeignClient(name = "core-service", url = "http://localhost:8000", configuration = FeignClientConfig.class)
/* 2. 직접 user-service 호출 */
//@FeignClient(name = "linkee-core-service",  configuration = FeignClientConfig.class)
public interface UserClient {

    /* UserService 에서 사용자 상태나 간단한 정보를 조회하는 API */
    @GetMapping("/api/v1/core-service/users/byId")
//    @GetMapping("/users/{userId}/grade")
    ResponseEntity<User> selectByUserId(@AuthenticationPrincipal CustomUser customUser);
}

