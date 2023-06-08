package com.mbronshteyn.usermanagement.service;

import com.mbronshteyn.usermanagement.entity.GuestEntity;
import com.mbronshteyn.usermanagement.entity.ThemeEntity;
import com.mbronshteyn.usermanagement.entity.ThemeEnum;
import com.mbronshteyn.usermanagement.entity.UserEntity;
import com.mbronshteyn.usermanagement.model.dto.UserDto;
import com.mbronshteyn.usermanagement.repository.GuestRepository;
import com.mbronshteyn.usermanagement.repository.ThemeRepository;
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
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository mockUserRepository;

    @Mock
    GuestRepository mockGuestRepository;

    @Mock
    ThemeRepository mockThemeRepository;

    @InjectMocks
    UserService userService;

    @Autowired
    BeanMapper beanMapper;

    UserDto userDto;
    UserEntity userEntity;
    GuestEntity guestEntity;

    ThemeEntity themeEntity;
    UserEntity userEntityB;


    @BeforeEach
    void setUp() {

        themeEntity = new ThemeEntity();
        themeEntity.setThemeEnum(ThemeEnum.DISCO);

        // workaround to get bean mapper into user service
        userService.setBeanMapper(beanMapper);

        userDto = new UserDto();
        userDto.setBatchNumber("123");
        userDto.setFirstName("Joe");
        userDto.setLastName("Doe");

        userEntity = new UserEntity();
        userEntity.setBatchNumber("123");
        userEntity.setFirstName("Joe");
        userEntity.setLastName("Doe");

        guestEntity = new GuestEntity();
        guestEntity.setBatchNumber("123");
        guestEntity.setFirstName("Joe");
        guestEntity.setLastName("Doe");

        userEntityB = new UserEntity();
        userEntityB.setBatchNumber("1234");
        userEntityB.setFirstName("Jane");
        userEntityB.setLastName("Dove");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createUser() {

        Mockito.when(mockUserRepository.save(any(UserEntity.class)))
                .thenReturn(userEntity);

        Mockito.when(mockGuestRepository.save(any(GuestEntity.class)))
                .thenReturn(guestEntity);

//        Mockito.when(mockThemeRepository.save(any(ThemeEntity.class)))
//                .thenReturn(themeEntity);

        UserDto actualUserDto = userService.createUser(userDto);

        verify(mockUserRepository, times(1)).save(any(UserEntity.class));

        assertEquals(userDto.getBatchNumber(), actualUserDto.getBatchNumber());
        assertEquals(userDto.getLastName(), actualUserDto.getLastName());
        assertEquals(userDto.getFirstName(), actualUserDto.getFirstName());
    }

    @Test
    void getUsersOrderByLastName() {
        List<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(userEntity);
        userEntityList.add(userEntityB);

        Sort sort = Sort.by(Sort.Direction.ASC, "lastName");

        doReturn(userEntityList).when(mockUserRepository).findAll(sort);

        List<UserDto> usersOrderByLastName = userService.getUsersOrderByLastName();

        verify(mockUserRepository, times(1)).findAll(sort);

        assertEquals(2, usersOrderByLastName.size());

        assertEquals(userEntity.getBatchNumber(), usersOrderByLastName.get(0).getBatchNumber());
    }

    @Test
    void findUserByIdWithResult() {

        when(mockUserRepository.findByBatchNumber("123")).thenReturn(userEntity);

        Optional<UserDto> userByIdOptional = userService.findBatchNumber("123");

        verify(mockUserRepository, times(1)).findByBatchNumber("123");

        assertTrue(userByIdOptional.isPresent());
        assertEquals("123", userByIdOptional.get().getBatchNumber());
    }

    @Test
    void findUserByIdWithNoResult() {

        when(mockUserRepository.findByBatchNumber("123")).thenReturn(null);

        Optional<UserDto> userByIdOptional = userService.findBatchNumber("123");

        verify(mockUserRepository, times(1)).findByBatchNumber("123");

        assertFalse(userByIdOptional.isPresent());
    }

    @Test
    void deleteByUserId() {
        when(mockUserRepository.deleteDistinctByBatchNumber("123")).thenReturn(1);

        int result = userService.deleteByUserId("123");

        verify(mockUserRepository, times(1)).deleteDistinctByBatchNumber("123");

        assertEquals(1, result);
    }
}