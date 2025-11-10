package com.example.linkeeuserservice.alarm_template.command.infrastructure.repository;

import com.example.linkeeuserservice.alarm_template.command.domain.aggregate.entity.AlarmTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmTemplateRepository extends JpaRepository<AlarmTemplate,Long> {
}
