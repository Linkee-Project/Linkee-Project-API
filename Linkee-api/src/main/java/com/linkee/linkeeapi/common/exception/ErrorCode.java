package com.linkee.linkeeapi.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum ErrorCode {
    /* 사용법
    * errorContent : entity명_오류_내용
    * errorCode: 다른 엔터티와 다른 자신의 고유 번호대 사용 (1000번 부터 시작)
    * message: "~다"로 끝나도록
    * HttpStatusCode: http오류코드 찾아서 넣어주세요 (404, 500 등등,,처리를 연결하기 위함)
    *
    * 자기가 생각하는 오류가 발생하는 곳에
    * throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND); 이런식으로 오류를 던지면 됩니다
    * */

    //공지사항 관련 오류(1000번대 사용)
    NOTICE_NOT_FOUND("1001", "해당 공지사항이 없습니다", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatusCode httpStatusCode;
}
