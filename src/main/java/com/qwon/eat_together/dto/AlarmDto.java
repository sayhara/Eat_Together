package com.qwon.eat_together.dto;

import com.qwon.eat_together.domain.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // NullPointerException 방지
public class AlarmDto {

    private boolean created; // 모임 개설
    
    private boolean result; // 신청 결과
    
    private boolean update; // 관심있는 곳

    public AlarmDto(Account account){
        this.created=account.isCreated();
        this.result=account.isResult();
        this.update=account.isUpdate();
    }
    
}
