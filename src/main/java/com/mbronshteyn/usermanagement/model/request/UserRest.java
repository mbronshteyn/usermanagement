package com.mbronshteyn.usermanagement.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRest {
    @NotBlank
    String userId;
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
}

