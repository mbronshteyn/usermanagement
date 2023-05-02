package com.mbronshteyn.usermanagement.controllers;

import com.mbronshteyn.usermanagement.model.request.UserRest;
import com.mbronshteyn.usermanagement.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    UserRest userRestJoe;
    UserRest userRestJane;
    UserRest userRestJack;

    @Mock
    UserService mockUserService;

    @BeforeEach
    public void setup() {
        // init data
        userRestJoe = UserRest.builder()
                .userId("123")
                .firstName("Joe")
                .lastName("Abc")
                .build();

        userRestJane = UserRest.builder()
                .userId("456")
                .firstName("Jane")
                .lastName("Cdf")
                .build();

        userRestJack = UserRest.builder()
                .userId("789")
                .firstName("Jack")
                .lastName("Xyz")
                .build();
    }

    @Test
    void createUser() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void deleteUser() {
    }
}