package com.qwon.eat_together.controller;

import com.qwon.eat_together.config.AuthUser;
import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.domain.Alarm;
import com.qwon.eat_together.repository.AlarmRepository;
import com.qwon.eat_together.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmRepository alarmRepository;
    private final AlarmService alarmService;

    @GetMapping("/alarms")
    public String getAlarm(@AuthUser Account account, Model model){
        List<Alarm> alarms =
                alarmRepository.findByAccountAndCheckedOrderByCreatedDateTimeDesc(account, false);
        long numberOfChecked = alarmRepository.countByAccountAndChecked(account, true);
        checkAlarmType(model, alarms, numberOfChecked, alarms.size());
        alarmService.readAlarm(alarms);
        model.addAttribute("isNew",true);
        return "alarm/list";
    }

    @GetMapping("alarms/old")
    public String getOldAlarm(@AuthUser Account account, Model model){
        List<Alarm> alarms =
                alarmRepository.findByAccountAndCheckedOrderByCreatedDateTimeDesc(account, true);
        long numberOfNotChecked = alarmRepository.countByAccountAndChecked(account, false);
        checkAlarmType(model, alarms, alarms.size(), numberOfNotChecked);
        model.addAttribute("isNew",false);
        return "alarm/list";
    }

    @DeleteMapping("/alarms")
    public String deleteAlarm(@AuthUser Account account){
        alarmRepository.deleteByAccountAndChecked(account,true);
        return "redirect:/alarm";
    }

    private void checkAlarmType(Model model, List<Alarm> alarms, long numberOfChecked, long numberOfNotChecked){
        List<Alarm> createdAlarms=new ArrayList<>();
        List<Alarm> updatedAlarms=new ArrayList<>();

        for(var alarm:alarms){
            switch (alarm.getAlarmType()){
                case MEETING_CREATED:
                    createdAlarms.add(alarm); break;
                case MEETING_UPDATED:
                    updatedAlarms.add(alarm); break;
            }
        }

        model.addAttribute("numberOfNotChecked",numberOfNotChecked);
        model.addAttribute("numberOfChecked",numberOfChecked);
        model.addAttribute("alarms",alarms);
        model.addAttribute("createdAlarms",createdAlarms);
        model.addAttribute("updatedAlarms",updatedAlarms);
    }
}
