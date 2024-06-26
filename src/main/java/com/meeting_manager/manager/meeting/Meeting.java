package com.meeting_manager.manager.meeting;

import java.time.LocalDate;

public record Meeting(
    String name,
    String responsiblePerson,
    String description,
    MeetingCategory category,
    MeetingType type,
    LocalDate startDate,
    LocalDate endDate
) {}
