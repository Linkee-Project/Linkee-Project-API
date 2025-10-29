package com.linkee.linkeeapi.notice.service;

import com.linkee.linkeeapi.common.enums.Role;
import com.linkee.linkeeapi.common.model.PageResponse;
import com.linkee.linkeeapi.notice.mapper.NoticeMapper;
import com.linkee.linkeeapi.notice.model.dto.request.CreateNoticeRequestDto;
import com.linkee.linkeeapi.notice.model.dto.response.NoticeResponseDto;
import com.linkee.linkeeapi.notice.model.entity.Notice;
import com.linkee.linkeeapi.notice.repository.NoticeRepository;
import com.linkee.linkeeapi.user.model.entity.User;
import com.linkee.linkeeapi.user.service.util.UserFinder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeServiceImpl implements NoticeService {

    private final UserFinder userFinder;
    private final ModelMapper modelMapper;
    private final NoticeRepository noticeRepository;
    private final NoticeMapper noticeMapper;

    //공지사항 등록
    //관리자만 등록 가능
    @Override
    public void createNotice(CreateNoticeRequestDto request) {

        User adminUser = userFinder.getById(request.getAdminId());

        if (adminUser.getUserRole() == Role.ADMIN) {
            Notice notice = modelMapper.map(request, Notice.class);
            notice.assignAdmin(adminUser);
            noticeRepository.save(notice);

        } else {
            throw new IllegalStateException("관리자만 공지사항 등록 가능");
        }

    }

    //공지사항 조회
    @Override
    public PageResponse<NoticeResponseDto> getNoticeList(int page, Integer size) {
        int pageSize = (size != null) ? size : 10;
        int offset = page * pageSize;

        List<NoticeResponseDto> notices = noticeMapper.findAll(offset, pageSize);
        int total = noticeMapper.countAll();

        return PageResponse.from(notices, page, pageSize, total);
    }
}
