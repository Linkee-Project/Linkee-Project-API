package com.linkee.linkeeapi.question.command.infrastructure;

import com.linkee.linkeeapi.question.command.domain.aggregate.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
