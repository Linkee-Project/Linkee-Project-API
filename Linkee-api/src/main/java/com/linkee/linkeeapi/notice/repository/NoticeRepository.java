package com.linkee.linkeeapi.notice.repository;

import com.linkee.linkeeapi.notice.model.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
