package com.meeting_manager.manager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.meeting_manager.manager.meeting.Meeting;
import com.meeting_manager.manager.meeting.MeetingCategory;
import com.meeting_manager.manager.meeting.MeetingController;
import com.meeting_manager.manager.meeting.MeetingRepository;
import com.meeting_manager.manager.meeting.MeetingType;
import com.meeting_manager.manager.users.MeetingAttendeeEntity;
import com.meeting_manager.manager.users.UserEntity;
import com.meeting_manager.manager.users.UserRepository;

@ExtendWith(MockitoExtension.class)
public class MeetingControllerTest {

    @InjectMocks
    private MeetingController meetingController;

    @Mock
    private MeetingRepository meetingRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void testFindAll() {
        List<Meeting> meetings = List.of(new Meeting("name", "responsiblePerson", "description", MeetingCategory.CodeMonkey, MeetingType.Live, LocalDate.now(), LocalDate.now().plusDays(1), List.of(new MeetingAttendeeEntity(new UserEntity()))));
        when(meetingRepository.findAll()).thenReturn(meetings);
        ResponseEntity<List<Meeting>> response = ResponseEntity.ok(meetings);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testFindByName() {
        Meeting meeting = new Meeting("name", "responsiblePerson", "description", MeetingCategory.CodeMonkey, MeetingType.Live, LocalDate.now(), LocalDate.now().plusDays(1), List.of(new MeetingAttendeeEntity(new UserEntity())));
        when(meetingRepository.findByName("name")).thenReturn(Optional.of(meeting));
        ResponseEntity<Meeting> responseEntity = meetingController.findByName("name");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Meeting responseBody = responseEntity.getBody();
        assertEquals(meeting.name(), responseBody.name());
    }

    @Test
    void testCreate() {
        Meeting meeting = new Meeting("name", "responsiblePerson", "description", MeetingCategory.CodeMonkey, MeetingType.Live, LocalDate.now(), LocalDate.now().plusDays(1), List.of(new MeetingAttendeeEntity(new UserEntity())));
        doNothing().when(meetingRepository).create(meeting);
        ResponseEntity<Meeting> response = meetingController.create(meeting);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(meeting, response.getBody());
    }

    @Test
    void testUpdate() {
        Meeting meeting = new Meeting("name", "responsiblePerson", "description", MeetingCategory.CodeMonkey, MeetingType.Live, LocalDate.now(), LocalDate.now().plusDays(1), List.of(new MeetingAttendeeEntity(new UserEntity())));
        doNothing().when(meetingRepository).update(meeting, "name");
        meetingController.update(meeting, "name");
    }

    @Test
    void testDelete() {
        doNothing().when(meetingRepository).delete("name");
        ResponseEntity<Void> response = meetingController.delete("name");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testAddAttendee() {
        Meeting meeting = new Meeting("name", "responsiblePerson", "description", MeetingCategory.CodeMonkey, MeetingType.Live, LocalDate.now(), LocalDate.now().plusDays(1), List.of(new MeetingAttendeeEntity(new UserEntity())));
        when(meetingRepository.findByName("name")).thenReturn(Optional.of(meeting));
        ResponseEntity<Void> response = meetingController.addAttendee("name", new UserEntity());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testGetMeetingAttendees() {
        Meeting meeting = new Meeting("name", "responsiblePerson", "description", MeetingCategory.CodeMonkey, MeetingType.Live, LocalDate.now(), LocalDate.now().plusDays(1), List.of(new MeetingAttendeeEntity(new UserEntity())));
        when(meetingRepository.findByName("name")).thenReturn(Optional.of(meeting));
        ResponseEntity<List<UserEntity>> response = meetingController.getMeetingAttendees("name");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
}