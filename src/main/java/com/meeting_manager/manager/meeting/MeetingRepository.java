package com.meeting_manager.manager.meeting;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.meeting_manager.manager.users.MeetingAttendeeEntity;

import jakarta.annotation.PostConstruct;

@Repository
public class MeetingRepository {
    private List<Meeting> meetings = new ArrayList<>();

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
        // You need to implement the logic to save the meeting attendee
        // For example, you can add the attendee to a meeting
        // meetings.get(0).addAttendee(meetingAttendee); // Replace 0 with the actual meeting index
    }

    @PostConstruct
    public void init() {
        meetings.add(new Meeting("Sample Meeting", "Responsible Person", "This is a sample meeting", MeetingCategory.CodeMonkey, MeetingType.Live, LocalDate.now(), LocalDate.now().plusDays(1)));
    }
}