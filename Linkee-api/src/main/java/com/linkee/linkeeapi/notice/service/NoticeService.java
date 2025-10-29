package com.linkee.linkeeapi.notice.service;

import com.linkee.linkeeapi.notice.model.dto.request.CreateNoticeRequestDto;

public interface NoticeService {

    //CREATE
    void createNotice(CreateNoticeRequestDto request);
}
