package com.mbronshteyn.usermanagement.service;

import com.mbronshteyn.usermanagement.model.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

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

    public Collection<Object> getUsers() {
        return Collections.EMPTY_LIST;
    }

    public Optional<UserDto> findUserById(String id) {
        return null;
    }
}
