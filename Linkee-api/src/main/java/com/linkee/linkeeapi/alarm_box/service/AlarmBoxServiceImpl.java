package com.linkee.linkeeapi.alarm_box.service;

import com.linkee.linkeeapi.alarm_box.mapper.AlarmBoxMapper;
import com.linkee.linkeeapi.alarm_box.model.dto.request.AlarmBoxCreateRequest;
import com.linkee.linkeeapi.alarm_box.model.dto.request.AlarmBoxSearchRequest;
import com.linkee.linkeeapi.alarm_box.model.dto.response.AlarmBoxResponse;
import com.linkee.linkeeapi.alarm_box.model.entity.AlarmBox;
import com.linkee.linkeeapi.alarm_box.repository.AlarmBoxRepository;
import com.linkee.linkeeapi.alarm_template.model.dto.request.AlarmTemplateSearchRequest;
import com.linkee.linkeeapi.alarm_template.model.dto.response.AlarmTemplateResponse;
import com.linkee.linkeeapi.common.enums.Role;
import com.linkee.linkeeapi.common.enums.Status;
import com.linkee.linkeeapi.common.model.PageResponse;
import com.linkee.linkeeapi.user.model.entity.User;
import com.linkee.linkeeapi.user.repository.UserRepository;
import com.linkee.linkeeapi.user.service.util.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlarmBoxServiceImpl implements AlarmBoxService{

    private final AlarmBoxRepository alarmBoxRepository;
    private final AlarmBoxMapper alarmBoxMapper;
    private final UserRepository userRepository;


    @Override
    public PageResponse<AlarmBoxResponse> selectAllAlarmBox(AlarmBoxSearchRequest request) {

        int page = request.getPage() != null && request.getPage() >= 0 ? request.getPage() : 0;
        int size = request.getSize() != null && request.getSize() > 0 ? request.getSize() : 10;
        int offset = page * size;
        String isChecked = request.getIsChecked();

        // Mapper에 전달할 record 생성 (offset 포함)
        AlarmBoxSearchRequest requestMapper = new AlarmBoxSearchRequest(
                request.getKeyword(),
                page,
                size,
                offset,
                isChecked
        );

        List<AlarmBoxResponse> Boxes = alarmBoxMapper.selectAllAlarmBox(requestMapper);

        int total = alarmBoxMapper.countAlarmBox(requestMapper);

        return PageResponse.from(Boxes, page, size, total);
    }


    // id 값으로 단건 조회
    @Override
    public ResponseEntity<AlarmBoxResponse> selectAlarmTemplateByAlarmBoxId(Long alarmBoxId) {

        AlarmBoxResponse alarmBox = alarmBoxMapper.selectAlarmBoxByBoxId(alarmBoxId);

        return ResponseEntity.ok(alarmBox);
    }

    // alarmBox 생성
    @Override
    public void createAlarmBox(AlarmBoxCreateRequest request) {
        // 현재 데이터가 없으므로 user 는 임의로 집어넣어준다
        UserFinder foundUser = new UserFinder(userRepository);

        if(foundUser.getById(request.getUserId())== null) {
            User user = new User(request.getUserId(), "user001", "pass001", "짱이", LocalDateTime.now(), LocalDateTime.now(), Status.Y, Role.USER);
            userRepository.save(user);
        }
        AlarmBox alarmBox = AlarmBox.builder()
                .alarmBoxContent(request.getAlarmBoxContent())
                .user(foundUser.getById(request.getUserId()))
                .isChecked(Status.N)
                .build();

        alarmBoxRepository.save(alarmBox);
    }

    @Override
    public void checkedAlarmBox(Long alarmBoxId) {

        AlarmBox alarmBox = alarmBoxRepository.findById(alarmBoxId).orElseThrow();

        alarmBox.checkedAlarm();
    }


}
