package com.qwon.eat_together.repository;

import com.qwon.eat_together.domain.Meeting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface MeetingRepositorySearch {
    Page<Meeting> findByKeyword(String keyword, Pageable pageable);
}
