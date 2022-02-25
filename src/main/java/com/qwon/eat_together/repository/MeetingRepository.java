package com.qwon.eat_together.repository;

import com.qwon.eat_together.domain.Meeting;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface MeetingRepository extends JpaRepository<Meeting, Long>, MeetingRepositoryCustom {

    boolean existsByUrl(String url);

    Meeting findByUrl(String url);

    Meeting findMeetingWithManagersByUrl(String url);

    @EntityGraph(attributePaths = {"members","managers"})
    Meeting findMeetingWithManagersAndMembersById(Long id);

    Meeting findMeetingById(Long id);

    List<Meeting> findFirst9ByPublishedAndClosedOrderByPublishTimeDesc(boolean b, boolean b1);
}

