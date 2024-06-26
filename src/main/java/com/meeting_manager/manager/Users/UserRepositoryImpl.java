package com.meeting_manager.manager.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.meeting_manager.manager.users.MeetingAttendeeEntity;
import com.meeting_manager.manager.users.UserEntity;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private List<UserEntity> users = new ArrayList<>();

    @Override
    public Optional<UserEntity> findByName(String name) {
        return users.stream()
               .filter(user -> user.getName().equals(name))
               .findFirst();
    }

    @Override
    public List<UserEntity> findAllUsers() {
        return users;
    }

    @Override
    public void saveUser(UserEntity user) {
        users.add(user);
    }

    @Override
    public void saveMeetingAttendee(MeetingAttendeeEntity meetingAttendee) {
        // You can implement the logic to save the meeting attendee here
        // For example, you can add the attendee to a meeting
        // users.get(0).addAttendee(meetingAttendee); // Replace 0 with the actual user index
    }
}