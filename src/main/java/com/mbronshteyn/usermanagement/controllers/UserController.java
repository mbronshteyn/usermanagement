package com.mbronshteyn.usermanagement.controllers;

import com.mbronshteyn.usermanagement.model.dto.UserDto;
import com.mbronshteyn.usermanagement.model.request.ErrorResponse;
import com.mbronshteyn.usermanagement.model.request.UserRest;
import com.mbronshteyn.usermanagement.service.UserService;
import io.beanmapper.BeanMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    private BeanMapper beanMapper;

    public UserController(UserService userService, BeanMapper beanMapper) {
        this.userService = userService;
        this.beanMapper = beanMapper;
    }



    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRest userRest) {

        try {
            UserDto userDto = beanMapper.map(userRest, UserDto.class);

            UserDto response = userService.createUser(userDto);

            log.info("Creating user {}", userRest.toString());

            return ResponseEntity.status(HttpStatus.CREATED.value())
                    .body(beanMapper.map(response, UserRest.class));
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .errorMessage(e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .timeStamp(Instant.now().toString())
                    .build();
            log.error("Error creating user: {} : {}", userRest.toString(), e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRest> getUserById(@PathVariable String id) {
        log.info("getUserById: {}", id);
        Optional<UserDto> userDto = userService.findUserById(id);
        return userDto.map(dto -> ResponseEntity.ok(beanMapper.map(dto, UserRest.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserRest>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getUsersOrderByLastName().stream()
                .map(s -> beanMapper.map(s, UserRest.class))
                .peek(user -> log.info("returning user: {}", user.toString()))
                .collect(Collectors.toList()));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {
        ResponseEntity res;
        int result = userService.deleteByUserId(id);

        if (result == 1) {
            res = ResponseEntity.noContent().build();
            log.info("user {} deleted successfully", id);
        } else {
            res = ResponseEntity.notFound().build();
            log.info("user {} was not found", id);
        }
        return res;
    }

    @GetMapping("/hello")
    public ResponseEntity<Object> helloWorld() {
        return ResponseEntity.ok().build();
    }
}
