package com.meeting_manager.manager.meeting;

import java.time.LocalDate;

public class MeetingFilter {
    private String name;
    private String description;
    private String responsiblePerson;
    private MeetingCategory category;
    private MeetingType type;
    private LocalDate startDateFrom;
    private LocalDate startDateTo;
    private Integer attendeesFrom;
    private Integer attendeesTo;

    public MeetingFilter() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public MeetingCategory getCategory() {
        return category;
    }

    public void setCategory(MeetingCategory category) {
        this.category = category;
    }

    public MeetingType getType() {
        return type;
    }

    public void setType(MeetingType type) {
        this.type = type;
    }

    public LocalDate getStartDateFrom() {
        return startDateFrom;
    }

    public void setStartDateFrom(LocalDate startDateFrom) {
        this.startDateFrom = startDateFrom;
    }

    public LocalDate getStartDateTo() {
        return startDateTo;
    }

    public void setStartDateTo(LocalDate startDateTo) {
        this.startDateTo = startDateTo;
    }

    public Integer getAttendeesFrom() {
        return attendeesFrom;
    }

    public void setAttendeesFrom(Integer attendeesFrom) {
        this.attendeesFrom = attendeesFrom;
    }

    public Integer getAttendeesTo() {
        return attendeesTo;
    }

    public void setAttendeesTo(Integer attendeesTo) {
        this.attendeesTo = attendeesTo;
    }
}