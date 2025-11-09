package com.linkee.linkeechatservice.chat.command.domain.mapper;


import com.linkee.linkeechatservice.chat.command.domain.dto.query_dto.response.ChatRoomListResponseDto;
import com.linkee.linkeechatservice.chat.command.domain.dto.query_dto.response.GameRoomListResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatRoomMapper {
    // 채팅방 목록
    List<ChatRoomListResponseDto> findMyChatRooms(
            @Param("userId") Long userId,
            @Param("offset") int offset,
            @Param("size") int size
    );

    int countMyChatRooms(@Param("userId") Long userId);

    // 게임방 목록 (전체)
    List<GameRoomListResponseDto> findAllGameRooms(
            @Param("offset") int offset,
            @Param("size") int size
    );

    int countAllGameRooms();
}
