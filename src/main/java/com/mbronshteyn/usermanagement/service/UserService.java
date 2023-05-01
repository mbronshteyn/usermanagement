package com.mbronshteyn.usermanagement.service;

import com.mbronshteyn.usermanagement.model.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserService {

    /**
     * Create User
     *
     * @param userDto
     * @return
     */
    public UserDto createUser(UserDto userDto) {
        return userDto;
    }
}
