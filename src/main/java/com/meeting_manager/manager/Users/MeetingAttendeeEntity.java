package com.meeting_manager.manager.users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import java.sql.Timestamp;

@Entity
public class MeetingAttendeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private MeetingEntity meeting;
    @ManyToOne
    @JoinColumn(name = "attendee_id")
    private UserEntity attendee;
    @Column(nullable = false, updatable = false)
    private Timestamp addedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MeetingEntity getMeeting() {
        return meeting;
    }

    public void setMeeting(MeetingEntity meeting) {
        this.meeting = meeting;
    }

    public UserEntity getAttendee() {
        return attendee;
    }

    public void setAttendee(UserEntity attendee) {
        this.attendee = attendee;
    }

    public Timestamp getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Timestamp addedAt) {
        this.addedAt = addedAt;
    }
}