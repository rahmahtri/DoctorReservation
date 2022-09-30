package com.indocyber.DoctorReservation.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InsertAccountDTO {
    private String username;
    private String password;
    private String role;
}
