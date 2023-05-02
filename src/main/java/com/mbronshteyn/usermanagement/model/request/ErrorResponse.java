package com.mbronshteyn.usermanagement.model.request;

import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    String timeStamp;
    String status;
    String errorMessage;
}
