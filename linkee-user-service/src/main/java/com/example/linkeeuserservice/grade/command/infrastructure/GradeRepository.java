package com.example.linkeeuserservice.grade.command.infrastructure;

import com.example.linkeeuserservice.grade.command.domain.aggregate.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade,Long> {
}
