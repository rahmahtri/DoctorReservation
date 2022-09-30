package com.indocyber.DoctorReservation.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTokenDTO {
    private String username;

    private String role;

    private String token;
}
