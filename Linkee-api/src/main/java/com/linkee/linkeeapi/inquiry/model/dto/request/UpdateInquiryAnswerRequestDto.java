package com.linkee.linkeeapi.inquiry.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInquiryAnswerRequestDto {
    private Long inquiryId;
    private Long adminId;
    private String answerContent;
}
