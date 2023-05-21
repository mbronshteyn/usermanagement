package com.mbronshteyn.usermanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbronshteyn.usermanagement.model.dto.UserDto;
import com.mbronshteyn.usermanagement.model.request.UserRest;
import com.mbronshteyn.usermanagement.service.UserService;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    UserDto userDto;
    UserDto userDtoTwo;

    List<UserDto> dtoList;
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
        userRestJoe = UserRest.builder().batchNumber("123").firstName("Joe").lastName("Abc").build();

        userRestJane = UserRest.builder().batchNumber("456").firstName("Jane").lastName("Cdf").build();

        userRestJack = UserRest.builder().batchNumber("789").firstName("Jack").lastName("Xyz").build();

        userDto = new UserDto();
        userDto.setBatchNumber("1234");
        userDto.setFirstName("Joe");
        userDto.setLastName("Doe");

        userDtoTwo = new UserDto();
        userDtoTwo.setBatchNumber("123456");
        userDtoTwo.setFirstName("Jane");
        userDtoTwo.setLastName("Xyz");

        dtoList = new ArrayList<>();
        dtoList.add(userDto);
        dtoList.add(userDtoTwo);
    }

    @Test
    public void createUser() throws Exception {

        Mockito.doReturn(userDto).when(mockUserService).createUser(any(UserDto.class));

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
                .statusCode(HttpStatus.CREATED.value())
                .contentType("application/json")
                .extract()
                .response();

        verify(mockUserService, times(1)).createUser(any(UserDto.class));

        String actualUserId = response.jsonPath().getString("batchNumber");
        String actualFirstName = response.jsonPath().getString("firstName");
        String actualLastName = response.jsonPath().getString("lastName");

        Assertions.assertEquals(userDto.getBatchNumber(), actualUserId);
        Assertions.assertEquals(userDto.getFirstName(), actualFirstName);
        Assertions.assertEquals(userDto.getLastName(), actualLastName);
    }

    @Test
    public void createUserDuplicateError() throws Exception {

        when(mockUserService.createUser(any(UserDto.class)))
                .thenThrow(RuntimeException.class);

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
                .statusCode(500)
                .contentType("application/json")
                .extract()
                .response();

        verify(mockUserService, times(1)).createUser(any(UserDto.class));
    }

    @Test
    public void getUserById() {

        String userId = userDto.getBatchNumber();

        Mockito.when(mockUserService.findBatchNumber(userDto.getBatchNumber()))
                .thenReturn(Optional.of(userDto));

        Response response = given()
                .auth()
                .preemptive()
                .basic("root", "root")
                .contentType("application/json")
                .accept("application/json")
                .when()
                .get("/users/" + userId)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .response();

        verify(mockUserService, times(1)).findBatchNumber(userDto.getBatchNumber());

        String actualUserId = response.jsonPath().getString("batchNumber");
        String actualFirstName = response.jsonPath().getString("firstName");
        String actualLastName = response.jsonPath().getString("lastName");

        Assertions.assertEquals(userDto.getBatchNumber(), actualUserId);
        Assertions.assertEquals(userDto.getFirstName(), actualFirstName);
        Assertions.assertEquals(userDto.getLastName(), actualLastName);
    }

    @Test
    public void getUserByIdNotFound() {

        String batchNumber = userDto.getBatchNumber();

        Mockito.when(mockUserService.findBatchNumber(userDto.getBatchNumber()))
                .thenReturn(Optional.empty());

        given()
                .auth()
                .preemptive()
                .basic("root", "root")
                .when()
                .get("/users/" + batchNumber)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        verify(mockUserService, times(1)).findBatchNumber(userDto.getBatchNumber());
    }

    @Test
    public void getAllUsers() {

        Mockito.doReturn(dtoList).when(mockUserService).getUsersOrderByLastName();

        Response response= given()
                .auth()
                .preemptive()
                .basic("root", "root")
                .when()
                .get("/users")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType("application/json")
                .extract()
                .response();

        verify(mockUserService, times(1)).getUsersOrderByLastName();

        List<UserRest> list = response.jsonPath().get();

        Assertions.assertEquals(2, list.size());
    }

    @Test
    public void deleteUser() {
        String userId = userDto.getBatchNumber();

        Mockito.when(mockUserService.deleteByUserId(userDto.getBatchNumber()))
                .thenReturn(1);

        given()
                .auth()
                .preemptive()
                .basic("root", "root")
                .when()
                .delete("/users/" + userId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        verify(mockUserService, times(1)).deleteByUserId(userDto.getBatchNumber());
    }

    @Test
    public void deleteUserNoFound() {
        String userId = userDto.getBatchNumber();

        Mockito.when(mockUserService.deleteByUserId(userDto.getBatchNumber()))
                .thenReturn(0);

        given()
                .auth()
                .preemptive()
                .basic("root", "root")
                .when()
                .delete("/users/" + userId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        verify(mockUserService, times(1)).deleteByUserId(userDto.getBatchNumber());
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
                .when()
                .get("/users/hello")
                .then()
                .statusCode(200);
    }
}