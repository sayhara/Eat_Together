package com.qwon.eat_together.repository;

import com.qwon.eat_together.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MeetingRepository extends JpaRepository<Meeting, Long>, MeetingRepositorySearch {

    boolean existsByUrl(String url);

    Meeting findByUrl(String url);

    Meeting findMeetingWithManagersByUrl(String url);

}

