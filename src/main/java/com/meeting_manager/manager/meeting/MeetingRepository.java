package com.meeting_manager.manager.meeting;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.meeting_manager.manager.users.MeetingAttendeeEntity;
import com.meeting_manager.manager.users.MeetingEntity;
import com.meeting_manager.manager.users.UserEntity;
import com.meeting_manager.manager.users.UserRepository;

import jakarta.annotation.PostConstruct;

@Repository
public class MeetingRepository {
    private List<Meeting> meetings = new ArrayList<>();
    private List<MeetingAttendeeEntity> meetingAttendees = new ArrayList<>();
    private UserRepository userRepository;

    @Autowired
    public MeetingRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Meeting> findAll() {
        return meetings;
    }

    public Optional<Meeting> findByName(String name) {
        return meetings.stream()
                .filter(meeting -> meeting.name().equals(name))
                .findFirst();
    }

    private List<MeetingEntity> meetingEntities = new ArrayList<>();

    public Meeting create(Meeting meeting) {
        MeetingEntity meetingEntity = MeetingEntity.fromMeeting(meeting, userRepository);
        meetingEntities.add(meetingEntity);
        meetings.add(meeting);
        return meeting;
    }

    public List<MeetingEntity> findAllMeetingEntities() {
        return meetingEntities;
    }

    public Meeting update(Meeting meeting, String name) {
        Optional<Meeting> existingMeeting = findByName(name);
        if(existingMeeting.isPresent()) {
            int index = meetings.indexOf(existingMeeting.get());
            meetings.set(index, meeting);
            return meeting;
        }
        return null;
    }

    public boolean delete(String name) {
        boolean deleted = meetings.removeIf(meeting -> meeting.name().equals(name));
        return deleted;
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
        UserEntity attendee = new UserEntity();
        attendee.setName("Some Name");
        userRepository.saveUser(attendee);
        
        Meeting meeting = new Meeting("Sample Meeting", "Responsible Person", "This is a sample meeting", MeetingCategory.CodeMonkey, MeetingType.Live, LocalDate.now(), LocalDate.now().plusDays(1), List.of(new MeetingAttendeeEntity(attendee)));
        create(meeting);
    }

    public void updateMeetingEntity(MeetingEntity meetingEntity) {
        Optional<Meeting> existingMeeting = findByName(meetingEntity.getName());
        if (existingMeeting.isPresent()) {
            int index = meetings.indexOf(existingMeeting.get());
            meetings.set(index, Meeting.fromMeetingEntity(meetingEntity, userRepository));
        }
    }

    public static Meeting fromMeetingEntity(MeetingEntity meetingEntity, UserRepository userRepository) {
        List<MeetingAttendeeEntity> attendees = meetingEntity.getAttendees().stream()
             .map(MeetingAttendeeEntity::getAttendee)
             .map(attendee -> new MeetingAttendeeEntity(attendee))
             .collect(Collectors.toList());
        return new Meeting(
            meetingEntity.getName(),
            meetingEntity.getResponsiblePerson().getName(),
            meetingEntity.getDescription(),
            meetingEntity.getCategory(),
            meetingEntity.getType(),
            meetingEntity.getStartDate(),
            meetingEntity.getEndDate(),
            attendees
        );
    }



    public List<MeetingEntity> findMeetingEntitiesByFilter(MeetingFilter filter) {
        List<MeetingEntity> meetingEntities = findAllMeetingEntities();
        
        if (filter.getName()!= null) {
            meetingEntities = meetingEntities.stream()
                 .filter(meetingEntity -> meetingEntity.getName().toLowerCase().contains(filter.getName().toLowerCase()))
                 .collect(Collectors.toList());
        }
        
        if (filter.getDescription()!= null) {
            meetingEntities = meetingEntities.stream()
                 .filter(meetingEntity -> meetingEntity.getDescription().toLowerCase().contains(filter.getDescription().toLowerCase()))
                 .collect(Collectors.toList());
        }
        
        if (filter.getResponsiblePerson()!= null) {
            meetingEntities = meetingEntities.stream()
                 .filter(meetingEntity -> meetingEntity.getResponsiblePerson().getName().equals(filter.getResponsiblePerson()))
                 .collect(Collectors.toList());
        }
        
        if (filter.getCategory()!= null) {
            meetingEntities = meetingEntities.stream()
                 .filter(meetingEntity -> meetingEntity.getCategory() == filter.getCategory())
                 .collect(Collectors.toList());
        }
        
        if (filter.getType()!= null) {
            meetingEntities = meetingEntities.stream()
                 .filter(meetingEntity -> meetingEntity.getType() == filter.getType())
                 .collect(Collectors.toList());
        }
        
        if (filter.getStartDateFrom()!= null && filter.getStartDateTo()!= null) {
            meetingEntities = meetingEntities.stream()
                 .filter(meetingEntity -> meetingEntity.getStartDate().isAfter(filter.getStartDateFrom()) || meetingEntity.getStartDate().isEqual(filter.getStartDateFrom()))
                 .filter(meetingEntity -> meetingEntity.getStartDate().isBefore(filter.getStartDateTo()) || meetingEntity.getStartDate().isEqual(filter.getStartDateTo()))
                 .collect(Collectors.toList());
        } else if (filter.getStartDateFrom()!= null) {
            meetingEntities = meetingEntities.stream()
                 .filter(meetingEntity -> meetingEntity.getStartDate().isAfter(filter.getStartDateFrom()) || meetingEntity.getStartDate().isEqual(filter.getStartDateFrom()))
                 .collect(Collectors.toList());
        } else if (filter.getStartDateTo()!= null) {
            meetingEntities = meetingEntities.stream()
                 .filter(meetingEntity -> meetingEntity.getStartDate().isBefore(filter.getStartDateTo()) || meetingEntity.getStartDate().isEqual(filter.getStartDateTo()))
                 .collect(Collectors.toList());
        }
        
        return meetingEntities;
    }
}