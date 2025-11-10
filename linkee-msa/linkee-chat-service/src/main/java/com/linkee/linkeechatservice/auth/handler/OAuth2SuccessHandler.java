package com.linkee.linkeechatservice.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkee.linkeechatservice.auth.repository.AuthRedisRepository;
import com.linkee.linkeechatservice.common.security.jwt.JwtTokenProvider;
import com.linkee.linkeechatservice.common.security.service.CustomUserDetails;
import com.linkee.linkeechatservice.user.entity.User;
import com.linkee.linkeechatservice.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthRedisRepository authRedisRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        CustomUserDetails customUser = (CustomUserDetails) authentication.getPrincipal();

        String email = customUser.getEmail();
        String role = customUser.getUser().getUserRole().name();

        User user = userRepository.findByUserEmail(email).orElseThrow();
        //JWT 발급

        /*response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        objectMapper.writeValue(response.getWriter(), tokens);*/
    }
}
