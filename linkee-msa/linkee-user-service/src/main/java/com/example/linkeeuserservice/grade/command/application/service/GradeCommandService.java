package com.example.linkeeuserservice.grade.command.application.service;


import com.example.linkeeuserservice.grade.command.application.dto.request.UpdateGradeNameRequest;
import com.example.linkeeuserservice.grade.command.domain.aggregate.entity.Grade;
import com.example.linkeeuserservice.grade.command.infrastructure.GradeRepository;
import com.example.linkeeuserservice.common.exception.BusinessException;
import com.example.linkeeuserservice.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GradeCommandService {


    private final GradeRepository gradeRepository;


    @Transactional
    public Grade createGrade(String gradeName) {
        Grade grade = Grade.builder()
                .gradeName(gradeName)
                .build();
        return gradeRepository.save(grade);
    }


    @Transactional
    public void updateGradeName(UpdateGradeNameRequest request) {
        Grade grade = gradeRepository.findById(request.getGradeId())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_REQUEST,"해당 등급이 존재하지 않습니다"));
        grade.modifyGradeName(request.getGradeName());
    }


    @Transactional
    public void deleteGrade(Long gradeId) {
        if (!gradeRepository.existsById(gradeId)) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST,"해당 등급이 존재하지 않습니다");
        }
        gradeRepository.deleteById(gradeId);
    }
}
