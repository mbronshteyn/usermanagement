package com.mbronshteyn.usermanagement.model.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

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
}

