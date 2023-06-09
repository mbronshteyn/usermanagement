package com.mbronshteyn.usermanagement.model.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GuestRest {
    @NotBlank
    String batchNumber;
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;

    List<ClubRest> clubs;
}

