package com.linkee.linkeechatservice.chat.command.domain.dto.command_dto.request;

import com.linkee.linkeechatservice.chat.chat_command.chat_domain.entity.ChatRoomType;
import com.linkee.linkeechatservice.common.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomCreateRequestDto {
    @NotBlank
    private String chatRoomName;

    @NotNull
    private ChatRoomType chatRoomType;

    private Status isPrivate;

    private int roomCode;

    @NotNull
    private Long roomOwnerId;

    private Integer roomCapacity; //게임방의 경우 최대인원 5명

}
