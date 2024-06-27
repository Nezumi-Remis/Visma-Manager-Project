package com.meeting_manager.manager.meeting;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.meeting_manager.manager.users.MeetingAttendeeEntity;
import com.meeting_manager.manager.users.MeetingEntity;
import com.meeting_manager.manager.users.UserRepository;

public record Meeting(
    String name,
    String responsiblePerson,
    String description,
    MeetingCategory category,
    MeetingType type,
    LocalDate startDate,
    LocalDate endDate,
    List<MeetingAttendeeEntity> attendees
) {
    public Meeting {
        attendees = new ArrayList<>(attendees);
    }
    
    public List<MeetingAttendeeEntity> getAttendees() {
        return attendees;
    }

    public static Meeting fromMeetingEntity(MeetingEntity meetingEntity, UserRepository userRepository) {
        return new Meeting(
            meetingEntity.getName(),
            meetingEntity.getResponsiblePerson().getName(), 
            meetingEntity.getDescription(),
            meetingEntity.getCategory(),
            meetingEntity.getType(),
            meetingEntity.getStartDate(),
            meetingEntity.getEndDate(),
            new ArrayList<>(meetingEntity.getAttendees().stream()
                .map(MeetingAttendeeEntity::getUser) 
                .map(user -> new MeetingAttendeeEntity(user)) 
                .collect(Collectors.toList()))
        );
    }
}