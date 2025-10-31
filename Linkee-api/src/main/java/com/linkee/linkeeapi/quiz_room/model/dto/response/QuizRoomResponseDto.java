package com.linkee.linkeeapi.quiz_room.model.dto.response;


import com.linkee.linkeeapi.common.enums.RoomMode;
import com.linkee.linkeeapi.common.enums.RoomStatus;
import com.linkee.linkeeapi.common.enums.Status;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//  빠른 시작 응답
public class QuizRoomResponseDto {
    private Long quizRoomId;      // 퀴즈방 ID
    private String roomTitle;     // 제목
    private Long categoryId;      // 카테고리 ID
    private Long roomOwner;       // 방장 회원 번호
    private RoomStatus roomStatus; // 상태(W, P, E)
    private RoomMode roomMode;     // 모드(S, G)
    private Integer joinedCount;   // 현재 인원
    private Integer roomCapacity;  // 최대 인원
    private Status isPrivate;      // 공개 여부(Y/N)
    private Integer roomCode;      // 초대 코드 (비공개방만)
    private Integer roomQuizLimit; // 문제 개수
    private LocalDateTime createdAt; // 생성 시간
    private LocalDateTime endedAt;   // 종료 시간
}
