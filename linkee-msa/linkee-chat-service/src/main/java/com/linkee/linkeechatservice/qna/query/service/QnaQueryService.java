package com.linkee.linkeechatservice.qna.query.service;


import com.linkee.linkeechatservice.qna.query.dto.response.QnaResponseDto;

import java.util.List;

public interface QnaQueryService {
    List<QnaResponseDto> getQnaListByRoomId(Long roomId);
}
