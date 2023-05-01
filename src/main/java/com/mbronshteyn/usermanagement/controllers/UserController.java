package com.mbronshteyn.usermanagement.controllers;

import com.mbronshteyn.usermanagement.model.dto.UserDto;
import com.mbronshteyn.usermanagement.model.request.UserRest;
import com.mbronshteyn.usermanagement.service.UserService;
import io.beanmapper.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BeanMapper beanMapper;



    @PostMapping
    public ResponseEntity<UserRest> createUser(@RequestBody UserRest userRest) {

        UserDto userDto = beanMapper.map(userRest, UserDto.class);

        UserDto response = userService.createUser(userDto);

        return ResponseEntity.ok()
                .body(beanMapper.map(response, UserRest.class));
    }
    @DeleteMapping
    public String deleteUser() {
        return "delete student was called";
    }

}
