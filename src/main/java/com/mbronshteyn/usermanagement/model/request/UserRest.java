package com.mbronshteyn.usermanagement.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRest {
    @NotBlank
    String batchNumber;
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;

    List<ClubRest> clubs;
}

