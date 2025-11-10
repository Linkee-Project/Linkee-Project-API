package com.linkee.linkeechatservice.chat.command.domain.dto.command_dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MarkAsReadRequest {
    private Long chatRoomId;
    private Long userId;
}
