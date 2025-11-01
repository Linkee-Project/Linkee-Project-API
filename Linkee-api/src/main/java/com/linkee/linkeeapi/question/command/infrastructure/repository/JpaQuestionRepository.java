package com.linkee.linkeeapi.question.command.infrastructure.repository;

import com.linkee.linkeeapi.question.command.domain.aggregate.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaQuestionRepository extends JpaRepository<Question,Long> {
}
