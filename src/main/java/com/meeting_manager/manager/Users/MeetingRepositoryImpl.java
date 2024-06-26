package com.meeting_manager.manager.users;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.meeting_manager.manager.meeting.MeetingRepository;

public class MeetingRepositoryImpl extends MeetingRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveMeetingAttendee(MeetingAttendeeEntity meetingAttendee) {
        entityManager.persist(meetingAttendee);
    }
}