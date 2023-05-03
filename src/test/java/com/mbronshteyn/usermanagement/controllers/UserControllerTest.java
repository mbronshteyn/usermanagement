package com.mbronshteyn.usermanagement.controllers;

import com.mbronshteyn.usermanagement.model.request.UserRest;
import com.mbronshteyn.usermanagement.service.UserService;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class UserControllerTest {

    UserRest userRestJoe;
    UserRest userRestJane;
    UserRest userRestJack;

    @Mock
    UserService mockUserService;

    @BeforeEach
    public void setup() {

        RestAssured.port = 8080;
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName("root");
        authScheme.setPassword("root");
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

    /**
     * Sample test with Rest Assured
     */
    @Test
    @Disabled
    void helloWorld() {
        given()
                .auth()
                .preemptive()
                .basic("root", "root")
                .when().get("/users/hello")
                .then()
                .statusCode(200);
    }
}