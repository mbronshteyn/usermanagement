package com.mbronshteyn.usermanagement.service;

import com.mbronshteyn.usermanagement.entity.UserEntity;
import com.mbronshteyn.usermanagement.model.dto.UserDto;
import com.mbronshteyn.usermanagement.repository.UserRepository;
import io.beanmapper.BeanMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final BeanMapper beanMapper;

    public UserService(UserRepository userRepository, BeanMapper beanMapper) {
        this.userRepository = userRepository;
        this.beanMapper = beanMapper;
    }

    /**
     * Create User
     *
     * @param userDto
     * @return
     */
    public UserDto createUser(UserDto userDto) {

        UserEntity userEntity = beanMapper.map(userDto, UserEntity.class);

        UserEntity savedEntity = userRepository.save(userEntity);

        return beanMapper.map(savedEntity, UserDto.class);
    }

    /**
     * Select users and order by last name
     *
     * @return
     */
    public Collection<Object> getUsersOrderByLastName() {
        return Collections.EMPTY_LIST;
    }

    public Optional<UserDto> findUserById(String userId) {
        UserEntity byUserId = userRepository.findByUserId(userId);

        if (byUserId == null) {
            return Optional.empty();
        } else {
            return Optional.of(beanMapper.map(byUserId, UserDto.class));
        }
    }

    @Transactional
    public int deleteByUserId(String userId) {
        return userRepository.deleteDistinctByUserId(userId);
    }
}
