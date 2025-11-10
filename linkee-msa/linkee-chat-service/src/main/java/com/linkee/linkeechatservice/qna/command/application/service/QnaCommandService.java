package com.linkee.linkeechatservice.qna.command.application.service;


import com.linkee.linkeechatservice.qna.command.application.dto.request.CreateQnaRequestDto;

public interface QnaCommandService {

    void createQna(CreateQnaRequestDto request, Long userId);
}
