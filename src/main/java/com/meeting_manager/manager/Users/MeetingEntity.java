package com.meeting_manager.manager.users;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.meeting_manager.manager.meeting.Meeting;
import com.meeting_manager.manager.meeting.MeetingCategory;
import com.meeting_manager.manager.meeting.MeetingType;

@Entity
public class MeetingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private MeetingCategory category;
    @Enumerated(EnumType.STRING)
    private MeetingType type;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "responsible_person_id")
    private UserEntity responsiblePerson;
    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MeetingAttendeeEntity> attendees;

    public MeetingEntity() {}

    public static MeetingEntity fromMeeting(Meeting meeting, UserRepository userRepository) {
        MeetingEntity meetingEntity = new MeetingEntity();
        meetingEntity.name = meeting.name();
        meetingEntity.description = meeting.description();
        meetingEntity.category = meeting.category();
        meetingEntity.type = meeting.type();
        meetingEntity.startDate = meeting.startDate();
        meetingEntity.endDate = meeting.endDate();
        meetingEntity.responsiblePerson = userRepository.findByName(meeting.responsiblePerson()).orElse(new UserEntity());
        meetingEntity.attendees = meeting.attendees(); // Initialize attendees list
        return meetingEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public UserEntity getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(UserEntity responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public List<MeetingAttendeeEntity> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<MeetingAttendeeEntity> attendees) {
        this.attendees = attendees;
    }
}