package com.linkee.linkeeapi.notice.model.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateNoticeRequestDto {
    @NotBlank String noticeTitle;
    @NotBlank String noticeContent;
    @NotBlank Long adminId; //작성자 ID
}
