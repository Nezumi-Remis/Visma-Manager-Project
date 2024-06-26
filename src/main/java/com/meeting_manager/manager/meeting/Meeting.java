package com.meeting_manager.manager.meeting;

import java.time.LocalDate;
import java.util.List;
import com.meeting_manager.manager.users.MeetingAttendeeEntity;

public record Meeting(
    String name,
    String responsiblePerson,
    String description,
    MeetingCategory category,
    MeetingType type,
    LocalDate startDate,
    LocalDate endDate,
    List<MeetingAttendeeEntity> attendees
) {}