package com.mbronshteyn.usermanagement.service;

import com.mbronshteyn.usermanagement.entity.UserEntity;
import com.mbronshteyn.usermanagement.model.dto.UserDto;
import com.mbronshteyn.usermanagement.repository.UserRepository;
import io.beanmapper.BeanMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository mockUserRepository;

    @InjectMocks
    UserService userService;

    @Autowired
    BeanMapper beanMapper;

    UserDto userDto;
    UserEntity userEntity;

    @BeforeEach
    void setUp() {

        userService.setBeanMapper(beanMapper);

        userDto = new UserDto();
        userDto.setUserId("123");
        userDto.setFirstName("Joe");
        userDto.setLastName("Doe");

        userEntity = new UserEntity();
        userEntity.setUserId("123");
        userEntity.setFirstName("Joe");
        userEntity.setLastName("Doe");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createUser() {

        Mockito.when(mockUserRepository.save(any(UserEntity.class)))
                .thenReturn(userEntity);

        UserDto actualUserDto = userService.createUser(userDto);

        verify(mockUserRepository, times(1)).save(any(UserEntity.class));

        assertEquals(userDto.getUserId(), actualUserDto.getUserId());
        assertEquals(userDto.getLastName(), actualUserDto.getLastName());
        assertEquals(userDto.getFirstName(), actualUserDto.getFirstName());
    }

    @Test
    void getUsersOrderByLastName() {
    }

    @Test
    void findUserByIdWithResult() {

        when(mockUserRepository.findByUserId("123")).thenReturn(userEntity);

        Optional<UserDto> userByIdOptional = userService.findUserById("123");

        verify(mockUserRepository).findByUserId("123");

        assertTrue(userByIdOptional.isPresent());
        assertEquals("123", userByIdOptional.get().getUserId());
    }

    @Test
    void findUserByIdWithNoResult() {

        when(mockUserRepository.findByUserId("123")).thenReturn(null);

        Optional<UserDto> userByIdOptional = userService.findUserById("123");

        verify(mockUserRepository).findByUserId("123");

        assertFalse(userByIdOptional.isPresent());
    }

    @Test
    void deleteByUserId() {
    }
}