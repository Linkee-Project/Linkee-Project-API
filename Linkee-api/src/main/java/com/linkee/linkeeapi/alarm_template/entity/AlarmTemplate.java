package com.linkee.linkeeapi.alarm_template.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AlarmTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;
}
