package com.meeting_manager.manager.meeting;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.meeting_manager.manager.meeting.MeetingFilter;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {
    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    public MeetingController(MeetingRepository meetingRepository, UserRepository userRepository) {
        this.meetingRepository = meetingRepository;
        this.userRepository = userRepository;
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
    
    @PostMapping("/{name}/attendees")
    public void addAttendee(@PathVariable String name, @RequestBody UserEntity attendee) {
        Meeting meeting = findByName(name);
        if (meeting!= null) {
            MeetingEntity meetingEntity = MeetingEntity.fromMeeting(meeting, userRepository);
            MeetingAttendeeEntity meetingAttendee = new MeetingAttendeeEntity(attendee);
            meetingAttendee.setMeeting(meetingEntity);
            meetingAttendee.setAddedAt(Timestamp.valueOf(LocalDateTime.now()));
            
            // Save the meetingAttendee to the database to generate the ID
            meetingRepository.saveMeetingAttendee(meetingAttendee);
            
            // Update the meeting attendees
            meeting.getAttendees().add(meetingAttendee);
            meetingRepository.updateMeetingEntity(MeetingEntity.fromMeeting(meeting, userRepository));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Meeting not found");
        }
    }

    @GetMapping("/{name}/attendees/list")
    public List<UserEntity> getMeetingAttendees(@PathVariable String name) {
        Meeting meeting = findByName(name);
        if (meeting!= null) {
            MeetingEntity meetingEntity = MeetingEntity.fromMeeting(meeting, userRepository);
            List<MeetingAttendeeEntity> meetingAttendees = meetingRepository.findMeetingAttendeesByMeeting(meetingEntity);
            List<UserEntity> attendees = new ArrayList<>();
            for (MeetingAttendeeEntity meetingAttendee : meetingAttendees) {
                attendees.add(meetingAttendee.getAttendee());
            }
            return attendees;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Meeting not found");
        }
    }


    @GetMapping("/filter")
    public List<Meeting> filterMeetings(@RequestBody MeetingFilter filter) {
        return meetingRepository.filterMeetings(filter);
    }
}