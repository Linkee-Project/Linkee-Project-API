/*
package com.linkee.linkeeapi.inquiry.service;

import com.linkee.linkeeapi.common.enums.Role;
import com.linkee.linkeeapi.common.model.PageResponse;
import com.linkee.linkeeapi.inquiry.model.dto.response.InquiryResponseDto;
import com.linkee.linkeeapi.inquiry.model.entity.Inquiry;
import com.linkee.linkeeapi.inquiry.repository.InquiryRepository;
import com.linkee.linkeeapi.user.model.entity.User;
import com.linkee.linkeeapi.user.repository.UserRepository;
import com.linkee.linkeeapi.user.service.util.UserFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class InquiryServiceImplTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InquiryRepository inquiryRepository;

    private InquiryService inquiryService;


    private User admin;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        // 유저 생성
        admin = userRepository.save(User.builder()
                .userLoginId("admin")
                .userPw("admin123")
                .userNickname("관리자")
                .userRole(Role.ADMIN)
                .build());

        user1 = userRepository.save(User.builder()
                .userLoginId("user1")
                .userPw("pw1")
                .userNickname("유저1")
                .userRole(Role.USER)
                .build());

        user2 = userRepository.save(User.builder()
                .userLoginId("user2")
                .userPw("pw2")
                .userNickname("유저2")
                .userRole(Role.USER)
                .build());

        // UserFinder mocking
        UserFinder userFinder = mock(UserFinder.class);
        when(userFinder.getById(admin.getUserId())).thenReturn(admin);
        when(userFinder.getById(user1.getUserId())).thenReturn(user1);
        when(userFinder.getById(user2.getUserId())).thenReturn(user2);

        // Service 생성
        ModelMapper modelMapper = new ModelMapper();
        inquiryService = new InquiryServiceImpl(inquiryRepository, userFinder, modelMapper);

        // 문의글 생성
        IntStream.range(1, 6).forEach(i -> inquiryRepository.save(Inquiry.builder()
                .inquiryTitle("문의 제목 " + i)
                .inquiryContent("문의 내용 " + i)
                .user(user1)
                .build()));

        IntStream.range(1, 4).forEach(i -> inquiryRepository.save(Inquiry.builder()
                .inquiryTitle("문의 제목 " + (i + 5))
                .inquiryContent("문의 내용 " + (i + 5))
                .user(user2)
                .build()));
    }


    @Test
    void admin_should_see_all_inquiries_service() {
        PageResponse<InquiryResponseDto> page = inquiryService.getInquiryList(0, 10, admin);
        assertThat(page.getTotalElements()).isEqualTo(8);
    }

    @Test
    void user_should_see_only_own_inquiries_service() {
        PageResponse<InquiryResponseDto> page = inquiryService.getInquiryList(0, 10, user1);
        assertThat(page.getTotalElements()).isEqualTo(5);
    }

}*/
