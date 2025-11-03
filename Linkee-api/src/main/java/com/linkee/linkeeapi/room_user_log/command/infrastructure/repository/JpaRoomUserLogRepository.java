package com.linkee.linkeeapi.room_user_log.command.infrastructure.repository;

import com.linkee.linkeeapi.room_user_log.command.domain.aggregate.RoomUserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRoomUserLogRepository extends JpaRepository<RoomUserLog, Long> {
}
