package com.meeting_manager.manager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.meeting_manager.manager.meeting.Meeting;
import com.meeting_manager.manager.meeting.MeetingCategory;
import com.meeting_manager.manager.meeting.MeetingController;
import com.meeting_manager.manager.meeting.MeetingFilter;
import com.meeting_manager.manager.meeting.MeetingRepository;
import com.meeting_manager.manager.meeting.MeetingType;
import com.meeting_manager.manager.users.MeetingAttendeeEntity;
import com.meeting_manager.manager.users.MeetingEntity;
import com.meeting_manager.manager.users.UserEntity;
import com.meeting_manager.manager.users.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MeetingRepositoryTest {

    @InjectMocks
    private MeetingRepository meetingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MeetingController meetingController;

    @Test
    void testFindAll() {
        List<Meeting> meetings = List.of(new Meeting("name", "responsiblePerson", "description", MeetingCategory.CodeMonkey, MeetingType.Live, LocalDate.now(), LocalDate.now().plusDays(1), List.of(new MeetingAttendeeEntity(new UserEntity()))));
        when(meetingRepository.findAll()).thenReturn(meetings);
        List<Meeting> response = meetingRepository.findAll();
        assertEquals(1, response.size());
    }

    @Test
    void testFindByName() {
        Meeting meeting = new Meeting("name", "responsiblePerson", "description", MeetingCategory.CodeMonkey, MeetingType.Live, LocalDate.now(), LocalDate.now().plusDays(1), List.of(new MeetingAttendeeEntity(new UserEntity())));
        when(meetingRepository.findByName("name")).thenReturn(Optional.of(meeting));
        Meeting response = meetingRepository.findByName("name").orElseThrow();
        assertEquals("name", response.name());
    }

    @Test
    void testCreate() {
        Meeting meeting = new Meeting("name", "responsiblePerson", "description", MeetingCategory.CodeMonkey, MeetingType.Live, LocalDate.now(), LocalDate.now().plusDays(1), List.of(new MeetingAttendeeEntity(new UserEntity())));
        when(meetingRepository.create(meeting)).thenReturn(meeting);
        ResponseEntity<Meeting> response = meetingController.create(meeting);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(meeting, response.getBody());
    }

    @Test
    void testUpdate() {
        Meeting meeting = new Meeting("name", "responsiblePerson", "description", MeetingCategory.CodeMonkey, MeetingType.Live, LocalDate.now(), LocalDate.now().plusDays(1), List.of(new MeetingAttendeeEntity(new UserEntity())));
        when(meetingRepository.update(meeting, "name")).thenReturn(meeting);
        Meeting response = meetingRepository.update(meeting, "name");
        assertEquals("name", response.name());
    }

    @Test
    void testDelete() {
        when(meetingRepository.delete("name")).thenReturn(true);
        boolean response = meetingRepository.delete("name");
        assertEquals(true, response);
    }

@Test
void testFilterMeetings() {
    Meeting meeting = new Meeting("name", "responsiblePerson", "description", MeetingCategory.CodeMonkey, MeetingType.Live, LocalDate.now(), LocalDate.now().plusDays(1), List.of(new MeetingAttendeeEntity(new UserEntity())));
    when(meetingRepository.findMeetingEntitiesByFilter(new MeetingFilter())).thenReturn(List.of(MeetingEntity.fromMeeting(meeting, userRepository)));
    List<Meeting> response = meetingRepository.findMeetingEntitiesByFilter(new MeetingFilter()).stream()
            .map(meetingEntity -> Meeting.fromMeetingEntity(meetingEntity, userRepository))
            .collect(Collectors.toList());
    assertEquals(1, response.size());
}
}