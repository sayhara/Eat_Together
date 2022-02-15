package com.qwon.eat_together.repository;

import com.querydsl.jpa.JPQLQuery;
import com.qwon.eat_together.domain.Meeting;
import com.qwon.eat_together.domain.QMeeting;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class MeetingRepositorySearchImpl
        extends QuerydslRepositorySupport implements MeetingRepositorySearch {

    public MeetingRepositorySearchImpl() {
        super(Meeting.class); // 상위 클래스
    }

    @Override
    public List<Meeting> findByKeyword(String keyword) {
        QMeeting meeting = QMeeting.meeting;
        JPQLQuery<Meeting> query=from(meeting).where(meeting.published.isTrue()
                .and(meeting.title.containsIgnoreCase(keyword)));
        return query.fetch();
    }
}
