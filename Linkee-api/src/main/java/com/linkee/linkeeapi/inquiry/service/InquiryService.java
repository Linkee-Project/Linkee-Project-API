package com.linkee.linkeeapi.inquiry.service;

import com.linkee.linkeeapi.common.model.PageResponse;
import com.linkee.linkeeapi.inquiry.model.dto.request.CreateInquiryRequestDto;
import com.linkee.linkeeapi.inquiry.model.dto.request.UpdateInquiryAnswerRequestDto;
import com.linkee.linkeeapi.inquiry.model.dto.response.InquiryResponseDto;
import com.linkee.linkeeapi.user.model.entity.User;

public interface InquiryService {

    //CREATE
    void createInquiry(CreateInquiryRequestDto createInquiryRequestDto);

    //READ
    PageResponse<InquiryResponseDto> getInquiryList(int page, Integer size, User user);

    //UPDATE
     void updateInquiryAnswer(UpdateInquiryAnswerRequestDto  request);

    //DELETE

}
