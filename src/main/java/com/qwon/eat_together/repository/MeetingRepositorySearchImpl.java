package com.qwon.eat_together.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import com.qwon.eat_together.domain.Meeting;
import com.qwon.eat_together.domain.QAccount;
import com.qwon.eat_together.domain.QMeeting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class MeetingRepositorySearchImpl
        extends QuerydslRepositorySupport implements MeetingRepositorySearch {

    public MeetingRepositorySearchImpl() {
        super(Meeting.class); // 상위 클래스
    }

    @Override
    public Page<Meeting> findByKeyword(String keyword, Pageable pageable) {
        QMeeting meeting = QMeeting.meeting;
        JPQLQuery<Meeting> query=from(meeting).where(meeting.published.isTrue()
                .and(meeting.title.containsIgnoreCase(keyword)))
                .leftJoin(meeting.managers, QAccount.account).fetchJoin().distinct();
        JPQLQuery<Meeting> pageQuery = getQuerydsl().applyPagination(pageable, query);
        QueryResults<Meeting> fetchResults = pageQuery.fetchResults();
        return new PageImpl<>(fetchResults.getResults(),pageable,fetchResults.getTotal());
    }
}
