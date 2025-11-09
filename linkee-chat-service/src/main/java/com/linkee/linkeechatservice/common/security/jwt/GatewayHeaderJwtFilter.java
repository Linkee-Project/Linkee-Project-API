package com.linkee.linkeechatservice.common.security.jwt;


import com.linkee.linkeechatservice.common.security.model.CustomUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class GatewayHeaderJwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Gateway에서 내려준 헤더 읽기
        String publicId = request.getHeader("X-User-PublicId");
        String email = request.getHeader("X-User-Email");
        String roleHeader = request.getHeader("X-User-Role");

        if (publicId != null && email != null && roleHeader != null) {

            System.out.println("role??" + roleHeader);

            // ROLE_ 접두사 확인
            String role = roleHeader.startsWith("ROLE_") ? roleHeader : "ROLE_" + roleHeader;

            // CustomUser 세팅
            CustomUser customUser = new CustomUser(null, publicId, email, role);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(customUser, null, customUser.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("GatewayHeaderJwtFilter: CustomUser 세팅 완료 -> " + email + " / " + role);
        } else {
            System.out.println("GatewayHeaderJwtFilter: 헤더 없음, 인증 생략");
        }

        filterChain.doFilter(request, response);
    }
}
