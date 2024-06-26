package com.meeting_manager.manager.users;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.meeting_manager.manager.users.MeetingAttendeeEntity;
import com.meeting_manager.manager.users.UserEntity;

@Repository
public interface UserRepository {
    Optional<UserEntity> findByName(String name);
    List<UserEntity> findAllUsers();
    void saveUser(UserEntity user);
    void saveMeetingAttendee(MeetingAttendeeEntity meetingAttendee);
}