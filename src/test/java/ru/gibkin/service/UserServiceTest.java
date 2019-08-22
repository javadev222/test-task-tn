package ru.gibkin.service;

import com.test_task_tn.TestTaskApplication;
import com.test_task_tn.business.dao.UserRepository;
import com.test_task_tn.business.domain.User;
import com.test_task_tn.business.service.impl.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestTaskApplication.class)
public class UserServiceTest {

    private static Long time = 123156789000L;
    private static Timestamp timestamp = new Timestamp(time);
    private static Boolean isOnline = true;
    private static UserService userService;
    private static final User SERGEY =
            User
                    .builder()
                    .id(6)
                    .name("SERGEY")
                    .email("sergey@gmail.com")
                    .isOnline(true)
                    .url("dsfsdf")
                    .statusTime(new Timestamp(113456789066L))
                    .build();
    private static final User IVAN = User
            .builder()
            .id(7)
            .name("IVAN")
            .email("IVAN@gmail.com")
            .isOnline(false)
            .url("dsfsdf")
            .statusTime(new Timestamp(123456789066L))
            .build();
    private static final User YURY = User
            .builder()
            .id(8)
            .name("YURY")
            .email("YURY@gmail.com")
            .isOnline(false)
            .url("dsfsdf")
            .statusTime(new Timestamp(123956789061L))
            .build();

    private static final List<User> users = Arrays.asList(SERGEY, IVAN, YURY);
    private static final List<User> timeAfterUsers = Arrays.asList(IVAN, YURY);

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUpMocks() {
        userService = new UserService(userRepository);
        when(userRepository.findById(eq(6L))).thenReturn(Optional.of(SERGEY));
        when(userRepository.findAll()).thenReturn(users);
        when(userRepository.findById(eq(7L))).thenReturn(Optional.of(IVAN));
        when(userRepository.findById(eq(8L))).thenReturn(Optional.of(YURY));
        when(userRepository.findByStatusTimeAfter(eq(timestamp))).thenReturn(timeAfterUsers);
        when(userRepository.findByIsOnline(eq(isOnline))).thenReturn(Collections.singletonList(SERGEY));
        when(userRepository.findByIsOnlineAndStatusTimeAfter(eq(isOnline), eq(timestamp))).thenReturn(new ArrayList<>());
    }

    @Test
    public void getUsersTimeAfter() {
        List<User> usersList = userService.getAll(time, null);
        assertEquals(timeAfterUsers, usersList);
    }

    @Test
    public void getUsersIsOnline() {
        List<User> usersList = userService.getAll(null, isOnline);
        assertEquals(Collections.singletonList(SERGEY), usersList);
    }

    @Test
    public void getUsersIsOnlineAndTimeAfter() {
        List<User> usersList = userService.getAll(time, isOnline);
        assertEquals(new ArrayList<>(), usersList);
    }

    @Test
    public void getAllUsers() {
        List<User> usersAll = userService.getAll();
        assertEquals(users, usersAll);
    }

    @Test
    public void getUser() {
        Optional<User> userSergey = userService.get(6L);
        assertEquals(Optional.of(SERGEY), userSergey);
        Optional<User> userIvan = userService.get(7L);
        assertEquals(Optional.of(IVAN), userIvan);
        Optional<User> userYury = userService.get(8L);
        assertEquals(Optional.of(YURY), userYury);
        Optional<User> user = userService.get(9L);
        assertEquals(Optional.empty(), user);
    }
}