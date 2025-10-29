package com.linkee.linkeeapi.inquiry.repository;

import com.linkee.linkeeapi.inquiry.model.entity.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    Page<Inquiry> findByUser_UserId(Long userId, Pageable pageable);
}
