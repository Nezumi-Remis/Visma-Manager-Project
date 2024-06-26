package com.meeting_manager.manager.meeting;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.meeting_manager.manager.users.MeetingAttendeeEntity;
import com.meeting_manager.manager.users.MeetingEntity;
import com.meeting_manager.manager.users.UserEntity;
import com.meeting_manager.manager.users.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {
    private final MeetingRepository meetingRepository;

    public MeetingController(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @GetMapping
    public List<Meeting> findAll() {
        return meetingRepository.findAll();
    }

    @GetMapping("/{name}")
    public Meeting findByName(@PathVariable String name) {
        Optional<Meeting> meeting = meetingRepository.findByName(URLDecoder.decode(name, StandardCharsets.UTF_8));
        if (meeting.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Meeting not found");
        }
        return meeting.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@Valid @RequestBody Meeting meeting) {
        meetingRepository.create(meeting);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{name}")
    public void update(@Valid @RequestBody Meeting meeting, @PathVariable String name) {
        meetingRepository.update(meeting, name);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{name}")
    public void delete(@PathVariable String name) {
        meetingRepository.delete(name);
    }

    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/{name}/attendees")
    public void addAttendee(@PathVariable String name, @RequestBody UserEntity attendee) {
        Optional<Meeting> meetingOptional = meetingRepository.findByName(URLDecoder.decode(name, StandardCharsets.UTF_8));
        if (meetingOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Meeting not found");
        }
        Meeting meeting = meetingOptional.get();
        MeetingEntity meetingEntity = new MeetingEntity(meeting);
        MeetingAttendeeEntity meetingAttendee = new MeetingAttendeeEntity();
        meetingAttendee.setMeeting(meetingEntity);
        meetingAttendee.setAttendee(attendee);
        meetingAttendee.setAddedAt(Timestamp.valueOf(LocalDateTime.now()));
        userRepository.saveMeetingAttendee(meetingAttendee); // Save the meeting attendee

        // Update the Meeting object in the meetings list
        List<MeetingAttendeeEntity> attendees = meetingEntity.getAttendees();
        if (attendees == null) {
            attendees = new ArrayList<>();
            meetingEntity.setAttendees(attendees);
        }
        attendees.add(meetingAttendee);
        meetingRepository.updateMeetingAttendees(meetingEntity);
    }

    @GetMapping("/{name}/attendees")
    public List<UserEntity> getAttendees(@PathVariable String name) {
        Optional<Meeting> meetingOptional = meetingRepository.findByName(URLDecoder.decode(name, StandardCharsets.UTF_8));
        if (meetingOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Meeting not found");
        }
        Meeting meeting = meetingOptional.get();
        MeetingEntity meetingEntity = new MeetingEntity(meeting);
        List<MeetingAttendeeEntity> meetingAttendees = meetingRepository.findMeetingAttendeesByMeeting(meetingEntity);
        List<UserEntity> attendees = new ArrayList<>();
        for (MeetingAttendeeEntity meetingAttendee : meetingAttendees) {
            attendees.add(meetingAttendee.getAttendee());
        }
        return attendees;
    }
}