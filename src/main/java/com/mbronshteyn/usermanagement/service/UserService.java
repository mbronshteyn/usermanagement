package com.mbronshteyn.usermanagement.service;

import com.mbronshteyn.usermanagement.entity.*;
import com.mbronshteyn.usermanagement.model.dto.ClubDto;
import com.mbronshteyn.usermanagement.model.dto.UserDto;
import com.mbronshteyn.usermanagement.repository.GuestRepository;
import com.mbronshteyn.usermanagement.repository.ThemeRepository;
import com.mbronshteyn.usermanagement.repository.UserRepository;
import io.beanmapper.BeanMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;

    private GuestRepository guestRepository;

    private ThemeRepository themeRepository;

    private BeanMapper beanMapper;

    public UserService(UserRepository userRepository, GuestRepository guestRepository,
                       ThemeRepository themeRepository, BeanMapper beanMapper) {
        this.userRepository = userRepository;
        this.guestRepository = guestRepository;
        this.themeRepository = themeRepository;
        this.beanMapper = beanMapper;
    }

    protected final void setBeanMapper(BeanMapper beanMapper) {
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
        ThemeEntity rockThemeEntity = new ThemeEntity();
        rockThemeEntity.setName(ThemeEnum.ROCK);

        List<ClubDto> clubs = userDto.getClubs();
        List<ClubEntity> clubEntityList = null;
        if (clubs != null && !clubs.isEmpty()) {
            clubEntityList = clubs.stream()
                    .map(clubDto -> {
                        ClubEntity clubEntity = beanMapper.map(clubDto, ClubEntity.class);
                        clubEntity.setUsers(userEntity);
                        clubEntity.setTheme(rockThemeEntity);
                        return clubEntity;
                    })
                    .collect(Collectors.toList());
            userEntity.setClubs(clubEntityList);
        }

        UserEntity savedEntity = userRepository.save(userEntity);

        ////////////
        ClubEntity clubEntity = new ClubEntity();
        clubEntity.setName("LA Fitness");
        List<ClubEntity> guestClubsList = new ArrayList<>();
        guestClubsList.add(clubEntity);

        GuestEntity guestEntity = new GuestEntity();
        guestEntity.setBatchNumber("123");
        guestEntity.setFirstName("Joe");
        guestEntity.setLastName("Doe");
        guestEntity.setClubs(guestClubsList);
        clubEntity.setGuests(guestEntity);


        ThemeEntity themeEntity = new ThemeEntity();
        themeEntity.setName(ThemeEnum.DISCO);

        clubEntity.setTheme(themeEntity);
        //     themeEntity.setClubs(guestClubsList);
        guestRepository.save(guestEntity);

        //////////

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
