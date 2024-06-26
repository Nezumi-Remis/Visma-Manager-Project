package com.meeting_manager.manager.meeting;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.meeting_manager.manager.users.MeetingAttendeeEntity;
import com.meeting_manager.manager.users.MeetingEntity;

import jakarta.annotation.PostConstruct;

@Repository
public class MeetingRepository {
    private List<Meeting> meetings = new ArrayList<>();
    private List<MeetingAttendeeEntity> meetingAttendees = new ArrayList<>();

    public List<Meeting> findAll() {
        return meetings;
    }

    public Optional<Meeting> findByName(String name) {
        return meetings.stream()
               .filter(meeting -> meeting.name().equals(name))
               .findFirst();
    }

    public void create(Meeting meeting) {
        meetings.add(meeting);
    }

    public void update(Meeting meeting, String name) {
        Optional<Meeting> existingMeeting = findByName(name);
        if(existingMeeting.isPresent()) {
            meetings.set(meetings.indexOf(existingMeeting.get()), meeting);
        }
    }

    public void delete(String name) {
        meetings.removeIf(meeting -> meeting.name().equals(name));
    }

    public void saveMeetingAttendee(MeetingAttendeeEntity meetingAttendee) {
        meetingAttendees.add(meetingAttendee);
    }

    public List<MeetingAttendeeEntity> findMeetingAttendeesByMeeting(MeetingEntity meeting) {
        return meetingAttendees.stream()
               .filter(meetingAttendee -> meetingAttendee.getMeeting().equals(meeting))
               .toList();
    }

    @PostConstruct
    public void init() {
        meetings.add(new Meeting("Sample Meeting", "Responsible Person", "This is a sample meeting", MeetingCategory.CodeMonkey, MeetingType.Live, LocalDate.now(), LocalDate.now().plusDays(1), new ArrayList<>()));
    }

    public void updateMeetingAttendees(MeetingEntity meetingEntity) {
        Meeting meetingToUpdate = meetings.stream()
               .filter(m -> m.name().equals(meetingEntity.getName()))
               .findFirst()
               .orElseThrow();
        
        List<MeetingAttendeeEntity> attendees = meetingEntity.getAttendees();
        if(attendees == null) {
            attendees = new ArrayList<>();
        }
        
        Meeting updatedMeeting = new Meeting(
                meetingToUpdate.name(),
                meetingToUpdate.responsiblePerson(),
                meetingToUpdate.description(),
                meetingToUpdate.category(),
                meetingToUpdate.type(),
                meetingToUpdate.startDate(),
                meetingToUpdate.endDate(),
                attendees
        );
        
        meetings.remove(meetingToUpdate);
        meetings.add(updatedMeeting);
    }
}

/*
 * package com.meeting_manager.manager.meeting;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.meeting_manager.manager.users.MeetingAttendeeEntity;
import com.meeting_manager.manager.users.MeetingEntity;

import jakarta.annotation.PostConstruct;

@Repository
public class MeetingRepository {
    private List<Meeting> meetings = new ArrayList<>();
    private List<MeetingAttendeeEntity> meetingAttendees = new ArrayList<>();

    public List<Meeting> findAll() {
        return meetings;
    }

    public Optional<Meeting> findByName(String name) {
        return meetings.stream()
                .filter(meeting -> meeting.name().equals(name))
                .findFirst();
    }

    public void create(Meeting meeting) {
        meetings.add(meeting);
    }

    public void update(Meeting meeting, String name) {
        Optional<Meeting> existingMeeting = findByName(name);
        if(existingMeeting.isPresent()) {
            meetings.set(meetings.indexOf(existingMeeting.get()), meeting);
        }
    }

    public void delete(String name) {
        meetings.removeIf(meeting -> meeting.name().equals(name));
    }

    public void saveMeetingAttendee(MeetingAttendeeEntity meetingAttendee) {
        meetingAttendees.add(meetingAttendee);
    }

    public List<MeetingAttendeeEntity> findMeetingAttendeesByMeeting(MeetingEntity meeting) {
        return meetingAttendees.stream()
                .filter(meetingAttendee -> meetingAttendee.getMeeting().equals(meeting))
                .toList();
    }

    @PostConstruct
    public void init() {
        meetings.add(new Meeting("Sample Meeting", "Responsible Person", "This is a sample meeting", MeetingCategory.CodeMonkey, MeetingType.Live, LocalDate.now(), LocalDate.now().plusDays(1)));
    }
}
 */