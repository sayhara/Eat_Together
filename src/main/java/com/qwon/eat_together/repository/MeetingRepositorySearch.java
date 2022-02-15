package com.qwon.eat_together.repository;

import com.qwon.eat_together.domain.Meeting;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface MeetingRepositorySearch {

    List<Meeting> findByKeyword(String keyword);

}
