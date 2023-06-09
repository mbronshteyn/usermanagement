package com.mbronshteyn.usermanagement.service;

import com.mbronshteyn.usermanagement.entity.ClubEntity;
import com.mbronshteyn.usermanagement.entity.GuestEntity;
import com.mbronshteyn.usermanagement.entity.ThemeEntity;
import com.mbronshteyn.usermanagement.entity.ThemeEnum;
import com.mbronshteyn.usermanagement.model.dto.ClubDto;
import com.mbronshteyn.usermanagement.model.dto.GuestDto;
import com.mbronshteyn.usermanagement.repository.GuestRepository;
import com.mbronshteyn.usermanagement.repository.ThemeRepository;
import io.beanmapper.BeanMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GuestService {

    private final GuestRepository guestRepository;

    private final ThemeRepository themeRepository;

    private BeanMapper beanMapper;

    public GuestService(GuestRepository guestRepository, ThemeRepository themeRepository,
                        BeanMapper beanMapper) {
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
     * @param guestDto
     * @return
     */
    public GuestDto createGuest(GuestDto guestDto) {

        GuestEntity guestEntity = beanMapper.map(guestDto, GuestEntity.class);
        ThemeEntity discoThemeEntity = new ThemeEntity();
        discoThemeEntity.setName(ThemeEnum.DISCO);

        List<ClubDto> clubs = guestDto.getClubs();
        List<ClubEntity> clubEntityList = null;
        if (clubs != null && !clubs.isEmpty()) {
            clubEntityList = clubs.stream()
                    .map(clubDto -> {
                        ClubEntity clubEntity = beanMapper.map(clubDto, ClubEntity.class);
                        clubEntity.setGuests(guestEntity);
                        clubEntity.setTheme(discoThemeEntity);
                        return clubEntity;
                    })
                    .collect(Collectors.toList());
            guestEntity.setClubs(clubEntityList);
        }

        GuestEntity savedEntity = guestRepository.save(guestEntity);


        return beanMapper.map(savedEntity, GuestDto.class);
    }
}
