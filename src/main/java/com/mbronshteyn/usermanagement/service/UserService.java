package com.mbronshteyn.usermanagement.service;

import com.mbronshteyn.usermanagement.entity.UserEntity;
import com.mbronshteyn.usermanagement.model.dto.UserDto;
import com.mbronshteyn.usermanagement.model.request.UserRest;
import com.mbronshteyn.usermanagement.repository.UserRepository;
import io.beanmapper.BeanMapper;
import io.beanmapper.config.BeanMapperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserService {

    private UserRepository userRepository;

    private BeanMapper beanMapper;

    public UserService(UserRepository userRepository, BeanMapper beanMapper) {
        this.userRepository = userRepository;
        this.beanMapper = beanMapper;
    }

    protected final  void setBeanMapper(BeanMapper beanMapper) {
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
    public List<UserDto> getUsersOrderByLastName() {
        Sort sort = Sort.by(Sort.Direction.ASC, "lastName");
        List<UserEntity> users = userRepository.findAll(sort);

        return users.stream()
                .map(s -> new BeanMapperBuilder().build().map(s, UserDto.class))
                .collect(Collectors.toList());

    }

    /**
     * Find user by user id in client request
     *
     * @param userId
     * @return
     */
    public Optional<UserDto> findUserById(String userId) {
        UserEntity byUserId = userRepository.findByUserId(userId);

        if (byUserId == null) {
            return Optional.empty();
        } else {
            return Optional.of(beanMapper.map(byUserId, UserDto.class));
        }
    }

    /**
     * Delete user by id which is specified in client request
     *
     * @param userId
     * @return
     */
    @Transactional
    public int deleteByUserId(String userId) {
        return userRepository.deleteDistinctByUserId(userId);
    }
}
