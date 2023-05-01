package com.mbronshteyn.usermanagement.controllers;

import com.mbronshteyn.usermanagement.model.dto.UserDto;
import com.mbronshteyn.usermanagement.model.request.UserRest;
import com.mbronshteyn.usermanagement.service.UserService;
import io.beanmapper.BeanMapper;
import io.beanmapper.config.BeanMapperBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final BeanMapper beanMapper;

    public UserController(UserService userService, BeanMapper beanMapper) {
        this.userService = userService;
        this.beanMapper = beanMapper;
    }


    @PostMapping
    public ResponseEntity<UserRest> createUser(@RequestBody UserRest userRest) {

        UserDto userDto = beanMapper.map(userRest, UserDto.class);

        UserDto response = userService.createUser(userDto);

        return ResponseEntity.ok()
                .body(beanMapper.map(response, UserRest.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRest> getUserById(@PathVariable String id) {
        Optional<UserDto> userDto = userService.findUserById(id);
        return userDto.map(dto -> ResponseEntity.ok(beanMapper.map(dto, UserRest.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserRest>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getUsers().stream()
                .map(s -> new BeanMapperBuilder().build().map(s, UserRest.class))
                .collect(Collectors.toList()));
    }


    @DeleteMapping
    public String deleteUser() {
        return "delete student was called";
    }

}
