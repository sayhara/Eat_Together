package com.qwon.eat_together.repository;

import com.qwon.eat_together.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    boolean existsByUrl(String url);
}
