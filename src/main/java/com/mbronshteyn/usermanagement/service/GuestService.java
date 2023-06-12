package com.mbronshteyn.usermanagement.service;

import com.mbronshteyn.usermanagement.entity.ClubEntity;
import com.mbronshteyn.usermanagement.entity.GuestEntity;
import com.mbronshteyn.usermanagement.entity.ThemeEntity;
import com.mbronshteyn.usermanagement.entity.ThemeEnum;
import com.mbronshteyn.usermanagement.model.dto.ClubDto;
import com.mbronshteyn.usermanagement.model.dto.GuestDto;
import com.mbronshteyn.usermanagement.repository.ClubRepository;
import com.mbronshteyn.usermanagement.repository.GuestRepository;
import com.mbronshteyn.usermanagement.repository.ThemeRepository;
import io.beanmapper.BeanMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    private BeanMapper beanMapper;

    private ClubRepository clubRepository;

    private ThemeRepository themeRepository;

    protected final void setBeanMapper(BeanMapper beanMapper) {
        this.beanMapper = beanMapper;
    }

    /**
     * Create User
     *
     * @param guestDto
     * @return
     */
    public GuestDto createGuest(GuestDto guestDto) {

        GuestEntity guestEntity = beanMapper.map(guestDto, GuestEntity.class);

        ThemeEntity discoThemeEntity = themeRepository.findByName(ThemeEnum.DISCO);
        if (discoThemeEntity == null) {
            discoThemeEntity = new ThemeEntity();
            discoThemeEntity.setName(ThemeEnum.DISCO);
        }

        final ThemeEntity finalThemeEntity = discoThemeEntity;

        List<ClubDto> clubs = guestDto.getClubs();

        // get clubs names
        Set<String> clubNames = clubs.stream()
                .map(ClubDto::getName)
                .collect(Collectors.toSet());

        clubRepository.findClubEntitiesByNameIn(clubNames);


        List<ClubEntity> clubEntityList = null;
        if (clubs != null && !clubs.isEmpty()) {
            clubEntityList = clubs.stream()
                    .map(clubDto -> {
                        ClubEntity clubEntity = beanMapper.map(clubDto, ClubEntity.class);
                        clubEntity.setGuests(guestEntity);
                        clubEntity.setTheme(finalThemeEntity);
                        return clubEntity;
                    })
                    .collect(Collectors.toList());
            guestEntity.setClubs(clubEntityList);
        }

        GuestEntity savedEntity = guestRepository.save(guestEntity);


        return beanMapper.map(savedEntity, GuestDto.class);
    }
}
