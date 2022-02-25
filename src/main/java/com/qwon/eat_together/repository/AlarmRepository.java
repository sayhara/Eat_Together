package com.qwon.eat_together.repository;

import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    @Transactional
    long countByAccountAndChecked(Account account, boolean checked);

    @Transactional
    void deleteByAccountAndChecked(Account account, boolean checked);

    @Transactional
    List<Alarm> findByAccountAndCheckedOrderByCreatedDateTimeDesc(Account account, boolean checked);
}
