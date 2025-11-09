package com.linkee.linkeechatservice.qna.command.infrastructure.repository;


import com.linkee.linkeechatservice.qna.command.domain.aggregate.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaQnaRepository extends JpaRepository<Qna, Integer> {
}
