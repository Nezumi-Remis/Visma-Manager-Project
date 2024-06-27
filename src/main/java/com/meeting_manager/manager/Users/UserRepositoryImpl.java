package com.meeting_manager.manager.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private List<UserEntity> users = new ArrayList<>();
    private List<MeetingAttendeeEntity> meetingAttendees = new ArrayList<>();

    @Override
    public Optional<UserEntity> findByName(String name) {
        return users.stream()
            .filter(user -> user!= null && user.getName()!= null && user.getName().equals(name))
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
        meetingAttendees.add(meetingAttendee);
    }
}