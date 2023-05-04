package com.mbronshteyn.usermanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbronshteyn.usermanagement.model.dto.UserDto;
import com.mbronshteyn.usermanagement.model.request.UserRest;
import com.mbronshteyn.usermanagement.service.UserService;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;


import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    UserRest userRestJoe;
    UserRest userRestJane;
    UserRest userRestJack;
    
    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    UserService mockUserService;

    @InjectMocks
    UserController userController;

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
    public void createUser() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setUserId("1234");
        userDto.setFirstName("Joe");
        userDto.setLastName("Doe");

        Mockito.doReturn(userDto).when(mockUserService).createUser(any());

        Response response = given()
                .auth()
                .preemptive()
                .basic("root", "root")
                .contentType("application/json")
                .accept("application/json")
                .body(objectMapper.writer().writeValueAsString(userRestJack).getBytes())
                .when()
                .post("/users")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .response();

        String actualUserId = response.jsonPath().getString("userId");
        String actualFirstName = response.jsonPath().getString("firstName");
        String actualLastName = response.jsonPath().getString("lastName");

        Assertions.assertEquals(userDto.getUserId(), actualUserId);
        Assertions.assertEquals(userDto.getFirstName(), actualFirstName);
        Assertions.assertEquals(userDto.getLastName(), actualLastName);

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