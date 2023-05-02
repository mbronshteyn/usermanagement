package com.mbronshteyn.usermanagement.controllers;

import com.mbronshteyn.usermanagement.model.dto.UserDto;
import com.mbronshteyn.usermanagement.model.request.ErrorResponse;
import com.mbronshteyn.usermanagement.model.request.UserRest;
import com.mbronshteyn.usermanagement.service.UserService;
import io.beanmapper.BeanMapper;
import io.beanmapper.config.BeanMapperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final BeanMapper beanMapper;

    public UserController(UserService userService, BeanMapper beanMapper) {
        this.userService = userService;
        this.beanMapper = beanMapper;
    }


    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRest userRest) {

        try {
            UserDto userDto = beanMapper.map(userRest, UserDto.class);

            UserDto response = userService.createUser(userDto);

            log.info("Creating user {}", userRest.toString());

            return ResponseEntity.ok()
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
        Optional<UserDto> userDto = userService.findUserById(id);
        return userDto.map(dto -> ResponseEntity.ok(beanMapper.map(dto, UserRest.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserRest>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getUsersOrderByLastName().stream()
                .map(s -> new BeanMapperBuilder().build().map(s, UserRest.class))
                .collect(Collectors.toList()));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {
        ResponseEntity res;
        int result = userService.deleteByUserId(id);

        if (result == 1) {
            res = ResponseEntity.noContent().build();
        } else {
            res = ResponseEntity.notFound().build();
        }
        return res;
    }

}
