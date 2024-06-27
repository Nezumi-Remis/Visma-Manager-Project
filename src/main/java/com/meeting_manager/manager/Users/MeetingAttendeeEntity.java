package com.meeting_manager.manager.users;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MeetingAttendeeEntity {

    public MeetingAttendeeEntity(UserEntity attendee1) {
    }
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public UserEntity getUser() {
        return user;
    }
    

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