package com.linkee.linkeechatservice.chat.command.domain.dto.query_dto.response;


import com.linkee.linkeechatservice.common.enums.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameRoomListResponseDto {

    private String chatRoomName;
    private Status isPrivate;
    private Long ownerId;
    private Integer joinedCount;
    private Integer roomCapacity;
}
