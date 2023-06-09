package com.mbronshteyn.usermanagement.controllers;

import com.mbronshteyn.usermanagement.model.dto.GuestDto;
import com.mbronshteyn.usermanagement.model.request.ErrorResponse;
import com.mbronshteyn.usermanagement.model.request.GuestRest;
import com.mbronshteyn.usermanagement.model.request.UserRest;
import com.mbronshteyn.usermanagement.service.GuestService;
import io.beanmapper.BeanMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Instant;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/guests")
public class GuestController {

    private GuestService guestService;
    private BeanMapper beanMapper;

    @PostMapping
    public ResponseEntity<?> createGuest(@Valid @RequestBody GuestRest guestRest) {

        try {
            GuestDto guestDto = beanMapper.map(guestRest, GuestDto.class);

            GuestDto response = guestService.createGuest(guestDto);

            log.info("Creating guest {}", guestDto.toString());

            return ResponseEntity.status(HttpStatus.CREATED.value())
                    .body(beanMapper.map(response, UserRest.class));
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .errorMessage(e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .timeStamp(Instant.now().toString())
                    .build();
            log.error("Error creating guest: {} : {}", guestRest.toString(), e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}
