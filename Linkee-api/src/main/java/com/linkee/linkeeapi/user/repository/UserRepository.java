package com.linkee.linkeeapi.user.repository;

import com.linkee.linkeeapi.inquiry.model.entity.Inquiry;
import com.linkee.linkeeapi.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
