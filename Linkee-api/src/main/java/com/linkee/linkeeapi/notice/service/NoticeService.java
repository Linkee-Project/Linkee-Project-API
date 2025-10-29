package com.linkee.linkeeapi.notice.service;

import com.linkee.linkeeapi.common.model.PageResponse;
import com.linkee.linkeeapi.notice.model.dto.request.CreateNoticeRequestDto;
import com.linkee.linkeeapi.notice.model.dto.response.NoticeResponseDto;

public interface NoticeService {

    //CREATE
    void createNotice(CreateNoticeRequestDto request);

    //READ
    PageResponse<NoticeResponseDto> getNoticeList(int page, Integer size);
}
