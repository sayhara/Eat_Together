package com.qwon.eat_together.controller;

import com.qwon.eat_together.config.AuthUser;
import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.domain.Alarm;
import com.qwon.eat_together.repository.AlarmRepository;
import com.qwon.eat_together.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmRepository alarmRepository;
    private final AlarmService alarmService;

    @GetMapping("/alarm")
    public String getAlarm(@AuthUser Account account, Model model){
        List<Alarm> alarms =
                alarmRepository.findByAccountAndCheckedOrderByCreatedTimeDesc(account, false);
        long checkedCount = alarmRepository.countByAccountAndChecked(account, true);
        checkAlarmType(model, alarms, checkedCount, alarms.size());
        alarmService.readAlarm(alarms);
        model.addAttribute("isRead",false);
        return "alarm/list";
    }

    @GetMapping("alarm/old")
    public String getOldAlarm(@AuthUser Account account, Model model){
        List<Alarm> alarms =
                alarmRepository.findByAccountAndCheckedOrderByCreatedTimeDesc(account, true);
        long notCheckedCount = alarmRepository.countByAccountAndChecked(account, false);
        checkAlarmType(model, alarms, alarms.size(), notCheckedCount);
        model.addAttribute("isRead",true);
        return "alarm/list";
    }

    @DeleteMapping("/alarm")
    public String deleteAlarm(@AuthUser Account account){
        alarmRepository.deleteByAccountAndChecked(account,true);
        return "redirect:/alarm";
    }

    private void checkAlarmType(Model model, List<Alarm> alarms, long checkedCount, long notCheckedCount){
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

        model.addAttribute("checkedCount",checkedCount);
        model.addAttribute("notCheckedCount",notCheckedCount);
        model.addAttribute("alarms",alarms);
        model.addAttribute("createdAlarms",createdAlarms);
        model.addAttribute("updatedAlarms",updatedAlarms);
    }
}
