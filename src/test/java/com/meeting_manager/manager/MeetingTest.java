package com.meeting_manager.manager;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.meeting_manager.manager.meeting.Meeting;
import com.meeting_manager.manager.users.MeetingAttendeeEntity;
import com.meeting_manager.manager.meeting.MeetingCategory;
import com.meeting_manager.manager.meeting.MeetingType;
import com.meeting_manager.manager.users.MeetingEntity;
import com.meeting_manager.manager.users.UserEntity;
import com.meeting_manager.manager.users.UserRepositoryImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class MeetingTest {

    @Test
    void testMeetingConstructor() {
        Meeting meeting = new Meeting("name", "responsiblePerson", "description", MeetingCategory.CodeMonkey, MeetingType.Live, LocalDate.now(), LocalDate.now().plusDays(1), List.of(new MeetingAttendeeEntity(new UserEntity())));
        assertNotNull(meeting);
        assertEquals("name", meeting.name());
        assertEquals("responsiblePerson", meeting.responsiblePerson());
        assertEquals("description", meeting.description());
        assertEquals(MeetingCategory.CodeMonkey, meeting.category());
        assertEquals(MeetingType.Live, meeting.type());
        assertEquals(LocalDate.now(), meeting.startDate());
        assertEquals(LocalDate.now().plusDays(1), meeting.endDate());
        assertEquals(1, meeting.attendees().size());
    }

    @Test
    void testFromMeetingEntity() {
        MeetingEntity meetingEntity = new MeetingEntity();
        meetingEntity.setName("name");
        meetingEntity.setDescription("description");
        meetingEntity.setCategory(MeetingCategory.CodeMonkey);
        meetingEntity.setType(MeetingType.Live);
        meetingEntity.setStartDate(LocalDate.now());
        meetingEntity.setEndDate(LocalDate.now().plusDays(1));
        meetingEntity.setResponsiblePerson(new UserEntity());
        meetingEntity.setAttendees(List.of(new MeetingAttendeeEntity(new UserEntity())));

        Meeting meeting = Meeting.fromMeetingEntity(meetingEntity, new UserRepositoryImpl());
        assertNotNull(meeting);
        assertEquals("name", meeting.name());
        assertEquals("description", meeting.description());
        assertEquals(MeetingCategory.CodeMonkey, meeting.category());
        assertEquals(MeetingType.Live, meeting.type());
        assertEquals(LocalDate.now(), meeting.startDate());
        assertEquals(LocalDate.now().plusDays(1), meeting.endDate());
        assertEquals(1, meeting.attendees().size());
    }
}