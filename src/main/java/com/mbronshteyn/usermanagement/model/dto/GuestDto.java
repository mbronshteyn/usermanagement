package com.mbronshteyn.usermanagement.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class GuestDto {
    private String batchNumber;
    private String firstName;
    private String lastName;

    private List<ClubDto> clubs;
}

