package com.qwon.eat_together.service;

import com.qwon.eat_together.domain.Alarm;
import com.qwon.eat_together.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;

    public void readAlarm(List<Alarm> alarms){
        alarms.forEach(n->n.setChecked(true));
        alarmRepository.saveAll(alarms);
    }

}
