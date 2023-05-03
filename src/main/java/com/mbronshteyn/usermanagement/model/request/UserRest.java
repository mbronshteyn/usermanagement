package com.mbronshteyn.usermanagement.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRest {
    String userId;
    String firstName;
    String lastName;
}

