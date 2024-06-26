package com.meeting_manager.manager.users;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;


public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByName(String name);
    void saveMeetingAttendee(MeetingAttendeeEntity meetingAttendee);
}