package com.linkee.linkeeapi.inquiry.repository;

import com.linkee.linkeeapi.inquiry.model.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}
