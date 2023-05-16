package com.mbronshteyn.usermanagement.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRest {
    @NotNull
    String userId;
    @NotNull
    String firstName;
    @NotNull
    String lastName;
}

