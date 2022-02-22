package com.qwon.eat_together.repository;

import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    long countByAccountAndChecked(Account account, boolean checked);

    List<Alarm> findByAccountAndCheckedOrderByCreatedTimeDesc(Account account, boolean checked);

    void deleteByAccountAndChecked(Account account, boolean checked);
}
