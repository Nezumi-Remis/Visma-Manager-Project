package com.meeting_manager.manager;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.meeting_manager.manager.users.UserEntity;
import com.meeting_manager.manager.users.UserRepository;
import com.meeting_manager.manager.users.UserRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.meeting_manager.manager.users.MeetingAttendeeEntity;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @Test
    void testFindByName(){
        UserEntity user = new UserEntity();
        user.setName("name");
        when(userRepository.findByName("name")).thenReturn(Optional.of(user));
        Optional<UserEntity> foundUser = userRepository.findByName("name");
        assertEquals("name", foundUser.get().getName());
    }

    @Test
    void testFindAllUsers() {
        when(userRepository.findAllUsers()).thenReturn(List.of(new UserEntity()));
        List<UserEntity> users = userRepository.findAllUsers();
        assertEquals(1, users.size());
    }

    @Test
    void testSaveUser() {
        UserEntity user = new UserEntity();
        user.setName("name");
        userRepository.saveUser(user);
        assertEquals("name", user.getName());
    }

    @Test
    void testSaveMeetingAttendee() {
        MeetingAttendeeEntity meetingAttendee = new MeetingAttendeeEntity(new UserEntity());
        userRepository.saveMeetingAttendee(meetingAttendee);
        assertNotNull(meetingAttendee);
    }
}