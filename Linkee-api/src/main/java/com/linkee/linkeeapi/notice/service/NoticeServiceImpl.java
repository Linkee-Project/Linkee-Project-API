package com.linkee.linkeeapi.notice.service;

import com.linkee.linkeeapi.common.enums.Role;
import com.linkee.linkeeapi.common.model.PageResponse;
import com.linkee.linkeeapi.notice.mapper.NoticeMapper;
import com.linkee.linkeeapi.notice.model.dto.request.CreateNoticeRequestDto;
import com.linkee.linkeeapi.notice.model.dto.response.NoticeDetailResponseDto;
import com.linkee.linkeeapi.notice.model.dto.response.NoticeListResponseDto;
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

    //공지사항 목록 조회
    //조회수 구현 필요
    @Override
    public PageResponse<NoticeListResponseDto> getNoticeList(int page, Integer size) {
        int pageSize = (size != null) ? size : 10;
        int offset = page * pageSize;

        List<NoticeListResponseDto> notices = noticeMapper.findAll(offset, pageSize);
        int total = noticeMapper.countAll();

        return PageResponse.from(notices, page, pageSize, total);
    }

    //공지사항 상세 조회
    @Override
    public NoticeDetailResponseDto getNoticeDetail(Long noticeId) {

        //조회수 증가
        noticeMapper.increaseViewCount(noticeId);

        //Id로 검색해서 상세조회
        NoticeDetailResponseDto notice = noticeMapper.findById(noticeId);

        if(notice == null){
            throw new IllegalStateException("존재하지 않는 공지사항 입니다");
        }
        return notice;
    }
}
