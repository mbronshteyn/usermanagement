package com.mbronshteyn.usermanagement.service;

import com.mbronshteyn.usermanagement.entity.ClubEntity;
import com.mbronshteyn.usermanagement.entity.UserEntity;
import com.mbronshteyn.usermanagement.model.dto.UserDto;
import com.mbronshteyn.usermanagement.repository.UserRepository;
import io.beanmapper.BeanMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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

        List<ClubEntity> clubEntities = new ArrayList<>();
        ClubEntity club = new ClubEntity();
        club.setName("new club");
        clubEntities.add(club);

        ClubEntity clubOne = new ClubEntity();
        clubOne.setName("new club one");
        clubEntities.add(clubOne);

        userEntity.setClubs(clubEntities);

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
                .map(s -> beanMapper.map(s, UserDto.class))
                .collect(Collectors.toList());

    }

    /**
     * Find user by user id in client request
     *
     * @param userId
     * @return
     */
    public Optional<UserDto> findBatchNumber(String userId) {
        UserEntity byUserId = userRepository.findByBatchNumber(userId);

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
        return userRepository.deleteDistinctByBatchNumber(userId);
    }
}
