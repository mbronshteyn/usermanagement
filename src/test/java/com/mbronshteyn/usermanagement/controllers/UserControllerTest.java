package com.mbronshteyn.usermanagement.controllers;

import com.mbronshteyn.usermanagement.model.request.UserRest;
import com.mbronshteyn.usermanagement.service.UserService;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;


import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    UserRest userRestJoe;
    UserRest userRestJane;
    UserRest userRestJack;

    @Mock
    UserService mockUserService;

    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    @BeforeEach
    public void setup() {

        configureRestAssured();

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
    public void createUser() {
    }

    @Test
    public void getUserById() {
    }

    @Test
    public void getAllUsers() {
    }

    @Test
    public void deleteUser() {
    }

    /**
     * Sample test with Rest Assured
     */
    @Test
    public void helloWorld() {
        given()
                .auth()
                .preemptive()
                .basic("root", "root")
                .when().get("/users/hello")
                .then()
                .statusCode(200);
    }
}